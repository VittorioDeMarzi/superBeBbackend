package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.AddressSaveDto;
import de.supercode.superBnB.dtos.PropertyRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.mappers.PropertyDtoMapper;
import de.supercode.superBnB.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;
    PropertyDtoMapper propertyDtoMapper;
    AddressService addressService;

    public PropertyService(PropertyRepository propertyRepository, PropertyDtoMapper propertyDtoMapper, AddressService addressService) {
        this.propertyRepository = propertyRepository;
        this.propertyDtoMapper = propertyDtoMapper;
        this.addressService = addressService;
    }

    // Implement CRUD operations for Property
    public PropertyResponseDto saveNewProperty(PropertyRequestDto dto) {
        AddressSaveDto addressDto = new AddressSaveDto(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city(),
                dto.country()
        );
        Address address = addressService.saveNewAddressIfDoesNotExist(addressDto);
        Property newProperty = createNewPropertyFromDto(dto, address);
        propertyRepository.save(newProperty);
        return propertyDtoMapper.apply(newProperty);
    }

    private Property createNewPropertyFromDto(PropertyRequestDto dto, Address address) {
        return new Property(
                dto.title(),
                dto.description(),
                dto.maxNumGuests(),
                dto.minPricePerNight(),
                address,
                dto.rooms()
        );
    }

    public void saveNewImage(long propertyId, String imageUrl) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NoSuchElementException("Property not found: " + propertyId));
        property.getPicUrls().add(imageUrl);
        propertyRepository.save(property);
    }

    public Property findPropertyById(Long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Property with id [%s] not found".formatted(id)));
    }

    public PropertyResponseDto findPropertyByIdDto(Long id) {
        return propertyRepository.findById(id)
                .map(propertyDtoMapper)
                .orElseThrow(() -> new NoSuchElementException("Property with id [%s] not found".formatted(id)));
    }

    public List<PropertyResponseDto> getAllProperties() {
        List<Property> allProperties = propertyRepository.findAll();
        return allProperties.stream()
                .map(propertyDtoMapper)
                .collect(Collectors.toList());
    }

    public List<PropertyResponseDto> getAllPublicProperties() {
        List<Property> allPublicProperties = propertyRepository.findByIsPublic(true);
        return allPublicProperties.stream()
                .map(propertyDtoMapper)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        propertyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Property not found with id: " + id));
        propertyRepository.deleteById(id);
    }

    public PropertyResponseDto updateProperty(Long id, PropertyRequestDto dto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Property not found with id: " + id));

        AddressSaveDto addressDto = new AddressSaveDto(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city(),
                dto.country());
        Address newAddress = addressService.saveNewAddressIfDoesNotExist(addressDto);

        property.setTitle(dto.title());
        property.setDescription(dto.description());
        property.setMaxNumGuests(dto.maxNumGuests());
        property.setRooms(dto.rooms());
        property.setAddress(newAddress);
        property.setMinPricePerNight(dto.minPricePerNight());
        propertyRepository.save(property);
        return propertyDtoMapper.apply(property);
    }

    public PropertyResponseDto changeVisibility(long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NoSuchElementException("Property not found with id: " + propertyId));

        if (!property.getPicUrls().isEmpty()) {
            throw new IllegalArgumentException("Property does not have any image yet. Change visibility not allowed");
        }
            property.setPublic(!property.isPublic());
            propertyRepository.save(property);
            return propertyDtoMapper.apply(property);
    }


}
