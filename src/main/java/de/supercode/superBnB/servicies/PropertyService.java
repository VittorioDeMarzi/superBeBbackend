package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.PropertyRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.entities.Address;
import de.supercode.superBnB.entities.Property;
import de.supercode.superBnB.mappers.PropertyDtoMapper;
import de.supercode.superBnB.repositories.AddressRepository;
import de.supercode.superBnB.repositories.PropertyRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;
    PropertyDtoMapper propertyDtoMapper;
    AddressRepository addressRepository;

    public PropertyService(PropertyRepository propertyRepository, PropertyDtoMapper propertyDtoMapper, AddressRepository addressRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyDtoMapper = propertyDtoMapper;
        this.addressRepository = addressRepository;
    }

    // Implement CRUD operations for Property
    public PropertyResponseDto saveNewProperty(PropertyRequestDto dto) {
        Address address = createNewAddressFromDto(dto);
        Property newProperty = createNewPropertyFromDto(dto, address);
        propertyRepository.save(newProperty);
        return propertyDtoMapper.apply(newProperty);
    }

    private Property createNewPropertyFromDto(PropertyRequestDto dto, Address address) {
        Property newProperty = new Property(
                dto.title(),
                dto.description(),
                dto.maxNumGuests(),
                dto.pricePerNight(),
                address
        );
        address.setProperty(newProperty);
        return newProperty;
    }

    private Address createNewAddressFromDto(PropertyRequestDto dto) {
        return new Address(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city());
    }


    public PropertyResponseDto findPropertyById(Long id) {
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

    public void deleteById(Long id) {
        propertyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Property not found with id: " + id));
        propertyRepository.deleteById(id);
    }

    public PropertyResponseDto updateProperty(Long id, PropertyResponseDto dto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Property not found with id: " + id));

        Address address = addressRepository.findByStreetAndHouseNumberAndZipCodeAndCity(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city()
        ). orElseGet(() -> {
            Address newAddress = new Address(dto.street(), dto.houseNumber(), dto.zipCode(), dto.city());
            addressRepository.save(newAddress);
            return newAddress;}
        );
        property.setTitle(dto.title());
        property.setDescription(dto.description());
        property.setMaxNumGuests(dto.maxNumGuests());
        property.setPricePerNight(dto.pricePerNight());
        property.setAddress(address);
        address.setProperty(property);
        propertyRepository.save(property);
        return propertyDtoMapper.apply(property);
    }

}
