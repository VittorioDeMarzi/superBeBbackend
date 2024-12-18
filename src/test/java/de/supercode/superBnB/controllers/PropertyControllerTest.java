package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.PropertyRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.dtos.SeasonalPriceRequestDto;
import de.supercode.superBnB.dtos.SeasonalPriceResponseDto;
import de.supercode.superBnB.servicies.PropertyService;
import de.supercode.superBnB.servicies.SeasonalPriceService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyControllerTest {
    // STRUKTUR
    // Triple A
    // (1) Arrange (Vorbereitung)
    // (2) Act (Ausführung)
    // (3) Assert (Überprüfung)

    // Mock Objekt = eine simulierte Version einer realen Klasse
    @Mock
    private static PropertyService mockPropertyService;

    @Mock
    private static SeasonalPriceService mockSeasonalPriceService;

    @InjectMocks
    private PropertyController propertyController;

    private static PropertyRequestDto examplePropertyRequestDto;
    private static PropertyResponseDto examplePropertyResponseDto;
    private static PropertyResponseDto examplePublicPropertyResponseDto;

    private static SeasonalPriceRequestDto exampleSeasonalPriceRequestDto;
    private static SeasonalPriceResponseDto exampleSeasonalPriceResponseDto;

    // erzeugen eine frische Testumgebung

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    public static void setUpBeforeClass() {
        examplePropertyRequestDto = new PropertyRequestDto(
                "Charming Apartment",
                "A lovely apartment located in the heart of the city with a great view.",
                "Main Street",
                "123",
                "12345",
                "Berlin",
                "Germany",
                3,
                4,
                new BigDecimal("150.00")
        );

        examplePropertyResponseDto = new PropertyResponseDto(
                1L,
                "Charming Apartment",
                "A lovely apartment located in the heart of the city with a great view.",
                "Main Street",
                "123",
                "12345",
                "Berlin",
                "Germany",
                3,
                4,
                new BigDecimal("150.00"),
                Collections.emptyList(),
                false
                );

        examplePublicPropertyResponseDto = new PropertyResponseDto(
                1L,
                "Charming Apartment",
                "A lovely apartment located in the heart of the city with a great view.",
                "Main Street",
                "123",
                "12345",
                "Berlin",
                "Germany",
                3,
                4,
                new BigDecimal("150.00"),
                Collections.emptyList(),
                true
        );

        exampleSeasonalPriceRequestDto = new SeasonalPriceRequestDto(
                LocalDate.of(2022, 6, 1),
                LocalDate.of(2022, 6, 30),
                new BigDecimal("100.00")
        );

        exampleSeasonalPriceResponseDto = new SeasonalPriceResponseDto(
                1L,
                1L,
                LocalDate.of(2022, 6, 1),
                LocalDate.of(2022, 6, 30),
                new BigDecimal("100.00")
        );
    }

    @Test
    public void testSaveNewProperty_makeNewProperty() {
        // (1) Arrange
        when(mockPropertyService.saveNewProperty(examplePropertyRequestDto)).thenReturn(examplePropertyResponseDto);
        // (2) Act
        ResponseEntity<PropertyResponseDto> result = propertyController.saveNewProperty(examplePropertyRequestDto);
        // (3) Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(examplePropertyResponseDto, result.getBody());
        verify(mockPropertyService, times(1)).saveNewProperty(examplePropertyRequestDto);
    }

    @Test
    public void testGetAllProperties() {
        // (1) arrange
        List<PropertyResponseDto> expectedProperties = List.of(examplePropertyResponseDto,
                new PropertyResponseDto(
                        2L,
                        "Second Apartment",
                        "Another lovely apartment.",
                        "Second Street",
                        "456",
                        "67890",
                        "Munich",
                        "Germany",
                        2,
                        3,
                        new BigDecimal("200.00"),
                        Collections.emptyList(),
                        false
                ));
        when(mockPropertyService.getAllProperties()).thenReturn(expectedProperties);
        // (2) act
        ResponseEntity<List<PropertyResponseDto>> result = propertyController.getAllProperties();
        // (3) assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedProperties, result.getBody());
        verify(mockPropertyService, times(1)).getAllProperties();
    }

    @Test
    public void testGetAllPublicProperties() {
        // (1) arrange
        List<PropertyResponseDto> expectedProperties = List.of(examplePublicPropertyResponseDto);
        when(mockPropertyService.getAllPublicProperties(8, 0)).thenReturn(expectedProperties);
        // (2) act
        ResponseEntity<List<PropertyResponseDto>> result = propertyController.getAllPublicProperties(8, 0);
        // (3) assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedProperties, result.getBody());
        verify(mockPropertyService, times(1)).getAllPublicProperties(8, 0);
    }

    @Test
    public void testGetPropertyById_returnBookWhenFound() {
        // (1) arrange
        when(mockPropertyService.findPropertyDtoById(1L)).thenReturn(examplePropertyResponseDto);
        // (2) act
        ResponseEntity<PropertyResponseDto> result = propertyController.getPropertyById(1L);
        // (3) assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(examplePropertyResponseDto, result.getBody());
        verify(mockPropertyService, times(1)).findPropertyDtoById(1L);
    }

    @Test
    public void testDeleteProperty() {
        // (1) arrange
        doNothing().when(mockPropertyService).deleteById(1L);
        // (2) act
        ResponseEntity<String> result = propertyController.deleteProperty(1L);
        // (3) assert
        assertEquals(HttpStatus.OK, result.getStatusCode());assertEquals("Property was deleted successfully", result.getBody());
        verify(mockPropertyService, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateProperty() {
        // (1) arrange
        Long propertyId = 1L;
        PropertyRequestDto updatePropertyRequestDto = new PropertyRequestDto(
                "Updated Apartment",
                "An updated lovely apartment located in the heart of the city with a great view.",
                "Updated Street",
                "789",
                "98765",
                "Frankfurt",
                "Germany",
                4,
                5,
                new BigDecimal("250.00")
        );
        PropertyResponseDto updatedPropertyResponseDto = new PropertyResponseDto(
                propertyId,
                updatePropertyRequestDto.title(),
                updatePropertyRequestDto.description(),
                updatePropertyRequestDto.street(),
                updatePropertyRequestDto.houseNumber(),
                updatePropertyRequestDto.zipCode(),
                updatePropertyRequestDto.city(),
                updatePropertyRequestDto.country(),
                updatePropertyRequestDto.rooms(),
                updatePropertyRequestDto.maxNumGuests(),
                updatePropertyRequestDto.minPricePerNight(),
                Collections.emptyList(),
                false
        );
        when(mockPropertyService.updateProperty(propertyId, updatePropertyRequestDto)).thenReturn(updatedPropertyResponseDto);
        // (2) act
        ResponseEntity<PropertyResponseDto> response = propertyController.updateProperty(propertyId, updatePropertyRequestDto);
        // (3) assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPropertyResponseDto, response.getBody());
        verify(mockPropertyService, times(1)).updateProperty(propertyId, updatePropertyRequestDto);
    }

    @Test
    public void testChangeVisibility(){
        // (1) arrange
        long propertyId = 1L;
        PropertyResponseDto publicPropertyResponseDto = new PropertyResponseDto(
                propertyId,
                examplePropertyResponseDto.title(),
                examplePropertyResponseDto.description(),
                examplePropertyResponseDto.street(),
                examplePropertyResponseDto.houseNumber(),
                examplePropertyResponseDto.zipCode(),
                examplePropertyResponseDto.city(),
                examplePropertyResponseDto.country(),
                examplePropertyResponseDto.rooms(),
                examplePropertyResponseDto.maxNumGuests(),
                examplePropertyResponseDto.minPricePerNight(),
                Collections.emptyList(),
                true
        );
        when(mockPropertyService.changeVisibility(propertyId)).thenReturn(publicPropertyResponseDto);

        // (2) Act
        ResponseEntity<PropertyResponseDto> response = propertyController.changeVisibility(propertyId);

        // (3) Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(publicPropertyResponseDto, response.getBody());
        verify(mockPropertyService, times(1)).changeVisibility(propertyId);
    }

    @Test
    public void testAddSeasonalPricePropertyWithId() {
        // (1) Arrange
        long propertyId = 1L;
        when(mockSeasonalPriceService.addSeasonalPrice(propertyId, exampleSeasonalPriceRequestDto)).thenReturn(exampleSeasonalPriceResponseDto);
        // (2) Act
        ResponseEntity<SeasonalPriceResponseDto> result = propertyController.addSeasonalPrice(propertyId, exampleSeasonalPriceRequestDto);
        // (3) Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(exampleSeasonalPriceResponseDto, result.getBody());
        verify(mockSeasonalPriceService, times(1)).addSeasonalPrice(propertyId,exampleSeasonalPriceRequestDto);
    }

}