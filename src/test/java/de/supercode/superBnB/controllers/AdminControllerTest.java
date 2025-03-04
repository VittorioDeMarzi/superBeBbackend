package de.supercode.superBnB.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.supercode.superBnB.dtos.ChangeRoleRequestDto;
import de.supercode.superBnB.dtos.UserFirstRegResponseDto;
import de.supercode.superBnB.servicies.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerTest {
    // STRUKTUR
    // Triple A
    // (1) Arrange (Vorbereitung)
    // (2) Act (Ausführung)
    // (3) Assert (Überprüfung)

    @Mock
    private static UserService mockUserService;

    @InjectMocks
    private AdminController adminController;

    @Autowired
    private MockMvc mockMvc;


    private static ChangeRoleRequestDto changeRoleRequest;
    private static UserFirstRegResponseDto expectedResponse;

    @BeforeAll
    public static void setUp() {
        changeRoleRequest = new ChangeRoleRequestDto("admin");
        expectedResponse = new UserFirstRegResponseDto(2L, "fakeUser@test.it", "admin");
    }

/*    @Test
    public void testChangeRole() {
        // (1) Arrange
        when(mockUserService.updateRole(2L, exampleChangeRequest)).thenReturn(exampleUserResponse);
        // (2) Act
        ResponseEntity<UserFirstRegResponseDto> result = adminController.changeUserRole(2L, exampleChangeRequest)
        // (3) Assert
    }*/


}