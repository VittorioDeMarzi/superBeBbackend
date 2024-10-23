package de.supercode.superBnB.integrations;

import com.jayway.jsonpath.JsonPath;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.entities.user.Role;
import de.supercode.superBnB.repositories.PropertyRepository;
import de.supercode.superBnB.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserRepository userRepository;

    private String userToken;
    private String adminToken;

    @BeforeEach
    public void setUp() throws Exception {

        // clean up database before each test
        propertyRepository.deleteAll();
        userRepository.deleteAll();

        // load test data
        Address address = new Address("Street 1", "1", "city 1", "12345", "Country 1");
        Property property = new Property();
        property.setId(1L);
        property.setTitle("Integration Test");
        property.setDescription("A test property for testing purposes");
        property.setMaxNumGuests(2);
        property.setMinPricePerNight(new BigDecimal("200"));
        property.setRooms(3);
        property.setAddress(address);
        property.setPublic(true);

        propertyRepository.save(property);

        if (userToken == null) {
            registerUser();
        } else {
            loginUser();
        }

        if (adminToken == null) {
            registerAdmin();
            } else {
            loginAdmin();
        }
    }

    // test cases for property integration
    private void registerUser() throws Exception {
        String userRegistrationJson = "{ \"username\": \"user@test.com\", \"password\": \"123\"}";

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType("application/json")
                .content(userRegistrationJson))
                .andExpect(status().isOk());

        loginUser();
    }

    private void loginUser()  throws Exception {
        String username = "user@test.com";
        String password = "123";
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        MvcResult result = mockMvc.perform(post("/api/v1/auth/signin")
               .header("Authorization", "Basic " + encodedAuth)
                .contentType("application/json"))
               .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        userToken = JsonPath.parse(jsonResponse).read("$.token");

        assertNotNull(userToken, "UserToken cannot be null");

    }

    private void registerAdmin() throws Exception {
        String userRegistrationJson = "{ \"username\": \"admin@test.com\", \"password\": \"123\"}";

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType("application/json")
                .content(userRegistrationJson))
                .andExpect(status().isOk());

        setRoleToAdmin();
        loginAdmin();
    }

    private void setRoleToAdmin() {
        // find user by username and set role to ADMIN
        userRepository.findByUsername("admin@test.com")
               .ifPresent(user -> {
                   user.setRole(Role.valueOf("ADMIN"));
                   userRepository.save(user);
               });

    }

    private void loginAdmin()  throws Exception {
        String username = "admin@test.com";
        String password = "123";
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        MvcResult result = mockMvc.perform(post("/api/v1/auth/signin")
               .header("Authorization", "Basic " + encodedAuth)
                .contentType("application/json"))
               .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        adminToken = JsonPath.parse(jsonResponse).read("$.token");

        assertNotNull(adminToken, "UserToken cannot be null");

    }

    @Test
    public void getPropertyById_successfully() throws Exception {
        Property property = propertyRepository.findAll().getFirst();

        mockMvc.perform(get("/api/v1/superbeb/property/" + property.getId())
               .header("Authorization", "Bearer " + userToken))
               .andExpect(jsonPath("$.title").value("Integration Test"));
    }


    @Test
    public void testGetPropertyById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/superbeb/property/999")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePropertyByAdmin_successfully() throws Exception {
        Property property = propertyRepository.findAll().getFirst();

        mockMvc.perform(delete("/api/v1/superbeb/property/" + property.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());

        assertEquals(0, propertyRepository.count());
    }

    @Test
    void testDeletePropertyByAdmin_NoFound() throws Exception {
        mockMvc.perform(delete("/api/v1/superbeb/property/999")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProperty_ReturnCreatedProperty() throws Exception {
        String jsonProperty = "{"
                + "\"title\": \"test title\","
                + "\"description\": \"test description\","
                + "\"street\": \"test street\","
                + "\"houseNumber\": \"123\","
                + "\"zipCode\": \"12345\","
                + "\"city\": \"test city\","
                + "\"country\": \"test country\","
                + "\"rooms\": 2,"
                + "\"maxNumGuests\": 2,"
                + "\"minPricePerNight\": 50"
                + "}";

        mockMvc.perform(post("/api/v1/superbeb/property")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProperty))
                .andExpect(jsonPath("$.title").value("test title"))
               .andExpect(status().isCreated());

        assertEquals(2, propertyRepository.count());
    }

    @Test
    void testUpdateProperty() throws Exception {
        Property property = propertyRepository.findAll().getFirst();

        String jsonUpdateProperty = "{"
                + "\"title\": \"new title\","
                + "\"description\": \"new description\","
                + "\"street\": \"new street\","
                + "\"houseNumber\": \"123\","
                + "\"zipCode\": \"12345\","
                + "\"city\": \"new city\","
                + "\"country\": \"new country\","
                + "\"rooms\": 2,"
                + "\"maxNumGuests\": 2,"
                + "\"minPricePerNight\": 50"
                + "}";

        mockMvc.perform(put("/api/v1/superbeb/property/" + property.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdateProperty))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("new title"))
                .andExpect(jsonPath("description").value("new description"));
    }



}
