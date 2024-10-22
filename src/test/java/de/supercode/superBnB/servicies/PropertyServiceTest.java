package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.mappers.PropertyDtoMapper;
import de.supercode.superBnB.repositories.PropertyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyServiceTest {

    @Mock
    private PropertyRepository mockPropertyRepository;
    @Mock
    private PropertyDtoMapper mockPropertyDtoMapper;
    // Mock-Objekt = simuliertes Objekt, das Verhalten eines echten Objekts nachahmt

    @InjectMocks
    private PropertyService propertyService;
    // reales Objekt




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveNewProperty() {

    }

    @Test
    void saveNewImage() {
    }

    @Test
    void findPropertyByIdDto() {
        // 1. Arrange
        Property property = new Property();
        property.setId(1L);
        property.setTitle("Test Property");
        property.setDescription("A test property for testing purposes");
        property.setMaxNumGuests(2);
        property.setMinPricePerNight(new BigDecimal("200"));
        property.setRooms(3);

        when(mockPropertyRepository.findById(property.getId())).thenReturn(Optional.of(property));

        // 2. Act
        PropertyResponseDto result = propertyService.findPropertyByIdDto(property.getId());
        // 3. Assert
        assertNotNull(result);
/*        assertEquals(result, propertyDtoMapper.apply(property));*/

    }

/*
    @Test
    void findPropertyByIdDto_NullId() {
        // 1. Arrange
        when(mockPropertyRepository.findById(null)).thenThrow(NullPointerException.class);

        // 2. Act & Assert
        assertThrows(NullPointerException.class, () -> propertyService.findPropertyByIdDto(null));
        verify(mockPropertyRepository, never()).findById(null);
    }
*/

    @Test
    void getAllProperties() {
    }

    @Test
    void getAllPublicProperties() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void updateProperty() {
    }

    @Test
    void changeVisibility() {
    }
}