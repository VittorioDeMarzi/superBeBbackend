package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.PropertySaveRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.entities.Address;
import de.supercode.superBnB.entities.Property;
import de.supercode.superBnB.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // Implement CRUD operations for Property
    public PropertyResponseDto saveNewProperty(PropertySaveRequestDto dto) {
        Address address = new Address(dto.street(), dto.houseNumber(), dto.zipCode(), dto.city());
        Property newProperty = new Property(dto.maxNumGuests(), dto.pricePerNight(), address);
        address.setProperty(newProperty);
        propertyRepository.save(newProperty);
        return new PropertyResponseDto(newProperty.getId(), address.getStreet(), address.getHouseNumber(), address.getZipCode(), address.getCity(), newProperty.getMaxNumGuests(), dto.pricePerNight());
    }

    public List<PropertyResponseDto> getAllProperties() {
        List<Property> allProperties = propertyRepository.findAll();
        return allProperties.stream()
                .map(property -> {
                    Address address = property.getAddress();
                    return new PropertyResponseDto(property.getId(), address.getStreet(), address.getHouseNumber(), address.getZipCode(), address.getCity(), property.getMaxNumGuests(), property.getPricePerNight());
                })
                .collect(Collectors.toList());
    }
}
