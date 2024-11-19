package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.AddressSaveDto;
import de.supercode.superBnB.dtos.PropertyRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.mappers.PropertyDtoMapper;
import de.supercode.superBnB.repositories.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyServiceTest {

    // Mock-Objekt = simuliertes Objekt, das Verhalten eines echten Objekts nachahmt
    @Mock
    private PropertyRepository mockPropertyRepository;
    @Mock
    private PropertyDtoMapper mockPropertyDtoMapper;
    @Mock AddressService mockAddressService;

    @InjectMocks
    private PropertyService propertyService;
    // reales Objekt

    private PropertyResponseDto expectedPropertyResponseDto;
    private PropertyRequestDto propertyRequestDto;
    private Property property;
    List<Property> properties;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        propertyRequestDto = new PropertyRequestDto(
                "Test Property",
                "A test property for testing purposes",
                "Test Street",
                "123",
                "12345",
                "Test City",
                "Test Country",
                3,
                2,
                new BigDecimal("200"));

        expectedPropertyResponseDto = new PropertyResponseDto(
                1L,
                "Test Property",
                "A test property for testing purposes",
                "Test Street",
                "123",
                "12345",
                "Test City",
                "Test Country",
                3,
                2,
                new BigDecimal("200"),
                Collections.emptyList(),
                false
        );

        Address address1 = new Address("Street 1", "1", "city 1", "12345", "Country 1");
        Address address2 = new Address("Street 2", "2", "City 2", "67890", "Country 2");

        property = new Property();
        property.setId(1L);
        property.setTitle("Test Property");
        property.setDescription("A test property for testing purposes");
        property.setMaxNumGuests(2);
        property.setMinPricePerNight(new BigDecimal("200"));
        property.setRooms(3);
        property.setAddress(address1);

        properties = Arrays.asList(
                new Property("Property 1", "Description 1", 3, new BigDecimal("100"), address1, 2),
                new Property("Property 2", "Description 2", 4, new BigDecimal("200"), address2, 4)
        );
    }

    @Test
    void saveNewProperty_successfully() {
        // 1. Arrange
        when(mockPropertyRepository.save(any(Property.class))).thenReturn(property);
        when(mockPropertyDtoMapper.apply(any(Property.class))).thenReturn(expectedPropertyResponseDto);
        // 2. Act
        PropertyResponseDto response = propertyService.saveNewProperty(propertyRequestDto);
        // 3. Assert
        assertNotNull(response);
        assertEquals("Test Property", response.title());
        assertEquals("A test property for testing purposes", response.description());
        verify(mockPropertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void saveNewImage() {
        // 1. Arrange
        long propertyId = 1L;
        String imageUrl = "https://example.com/image.jpg";
        Property property = new Property();
        property.setId(propertyId);
        property.setPicUrls(new ArrayList<>());
        when(mockPropertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(mockPropertyRepository.save(property)).thenReturn(property);
        // 2. Act
        propertyService.saveNewImage(propertyId, imageUrl);
        // 3. Assert
        assertEquals(1, property.getPicUrls().size());
        assertTrue(property.getPicUrls().contains(imageUrl));
        verify(mockPropertyRepository, times(1)).save(property);

    }

    @Test
    void findPropertyDtoById() {
        // 1. Arrange
         when(mockPropertyRepository.findById(property.getId())).thenReturn(Optional.of(property));
        when(mockPropertyDtoMapper.apply(property)).thenReturn(expectedPropertyResponseDto);
        // 2. Act
        PropertyResponseDto result = propertyService.findPropertyDtoById(property.getId());
        // 3. Assert
        assertNotNull(result);
        assertEquals(result, expectedPropertyResponseDto);
        verify(mockPropertyRepository, times(1)).findById(property.getId());

    }



    @Test
    void findPropertyDto_ById_NotFound() {
        // 1. Arrange
        when(mockPropertyRepository.findById(1L)).thenReturn(Optional.empty());

        // 2. Act & Assert
        assertThrows(NoSuchElementException.class, () -> propertyService.findPropertyDtoById(1L));
        verify(mockPropertyRepository, times(1)).findById(1L);
    }

    @Test
    void findPropertyById() {
        // 1. Arrange
        when(mockPropertyRepository.findById(1L)).thenThrow(NullPointerException.class);

        // 2. Act & Assert
        assertThrows(NullPointerException.class, () -> propertyService.findPropertyDtoById(null));
        verify(mockPropertyRepository, never()).findById(null);
    }

    @Test
    void getAllProperties() {
        // 1. Arrange
         List<PropertyResponseDto> expectedDtos = Arrays.asList(
                new PropertyResponseDto(1L, "Property 1", "Description 1", "", "", "", "", "", 2, 3, new BigDecimal("100"), Collections.emptyList(), false),
                new PropertyResponseDto(2L, "Property 2", "Description 2", "", "", "", "", "", 4, 4, new BigDecimal("200"), Collections.emptyList(), false)
        );
        when(mockPropertyRepository.findAll()).thenReturn(properties);
        when(mockPropertyDtoMapper.apply(properties.get(0))).thenReturn(expectedDtos.get(0));
        when(mockPropertyDtoMapper.apply(properties.get(1))).thenReturn(expectedDtos.get(1));
        List<PropertyResponseDto> result = propertyService.getAllProperties();

        // 3. Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDtos, result);
        verify(mockPropertyRepository, times(1)).findAll();

    }

    @Test
    void getAllPublicProperties() {
        // 1. Arrange
        Pageable pageable = PageRequest.of(0, 8);
        properties.get(0).setPublic(true);
        properties.get(1).setPublic(true);

        List<PropertyResponseDto> expectedDtos = Arrays.asList(
                new PropertyResponseDto(1L, "Property 1", "Description 1", "", "", "", "", "", 2, 3, new BigDecimal("100"), Collections.emptyList(), true),
                new PropertyResponseDto(2L, "Property 2", "Description 2", "", "", "", "", "", 4, 4, new BigDecimal("200"), Collections.emptyList(), true)
        );
        when(mockPropertyRepository.findByIsPublic(true, pageable)).thenReturn(properties);
        when(mockPropertyDtoMapper.apply(properties.get(0))).thenReturn(expectedDtos.get(0));
        when(mockPropertyDtoMapper.apply(properties.get(1))).thenReturn(expectedDtos.get(1));

        // 2. Act
        List<PropertyResponseDto> result = propertyService.getAllPublicProperties(8, 0);

        // 3. Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDtos, result);
        verify(mockPropertyRepository, times(1)).findByIsPublic(true, pageable);


    }

    @Test
    void deleteById() {
        when(mockPropertyRepository.existsById(1L)).thenReturn(true);
        propertyService.deleteById(1L);

        verify(mockPropertyRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateProperty_existingAddress_shouldUpdatePropertySuccessfully() {
        // 1. Arrange
        PropertyRequestDto newPropertyDto = new PropertyRequestDto(
                "Updated Property",
                "Updated Property",
                "Updated Street",
                "123",
                "12345",
                "Test City",
                "Test Country",
                5,
                2,
                new BigDecimal("200"));
        PropertyResponseDto expectedDto = new PropertyResponseDto(
                1L,
                "Updated Property",
                "Updated Property",
                "Updated Street",
                "123",
                "12345",
                "Test City",
                "Test Country",
                5,
                2,
                new BigDecimal("200"),
                Collections.emptyList(),
                false
        );

        Address updatedAddress = new Address("Updated Street", "123", "12345", "Test City", "Test Country");

        when(mockPropertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(mockPropertyRepository.save(any(Property.class))).thenReturn(property);
        when(mockAddressService.saveNewAddressIfDoesNotExist(any(AddressSaveDto.class))).thenReturn(updatedAddress);
        when(mockPropertyDtoMapper.apply(any(Property.class))).thenReturn(expectedDto);

        // 2. Act
        PropertyResponseDto response = propertyService.updateProperty(1L, newPropertyDto);

        // 3. Assert
        assertNotNull(response);
        assertEquals("Updated Property", response.title());
        assertEquals("Updated Street", response.street());
        verify(mockPropertyRepository, times(1)).save(any(Property.class));



    }

    @Test
    void changeVisibility() {
        PropertyResponseDto expectedDto = new PropertyResponseDto(
                1L,
                "Test Property",
                "A test property for testing purposes",
                "Test Street",
                "123",
                "12345",
                "Test City",
                "Test Country",
                3,
                2,
                new BigDecimal("200"),
                Collections.emptyList(),
                true
        );
        when(mockPropertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(mockPropertyDtoMapper.apply(property)).thenReturn(expectedDto);

        // 2. Act
        PropertyResponseDto result = propertyService.changeVisibility(1L);

        // 3. Assert
        assertNotNull(result);
        assertTrue(result.isPublic());
        assertEquals(expectedDto, result);

        verify(mockPropertyRepository, times(1)).findById(1L);
        verify(mockPropertyRepository, times(1)).save(property);
        verify(mockPropertyDtoMapper, times(1)).apply(property);
    }
}