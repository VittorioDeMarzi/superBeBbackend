package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.PropertySaveRequestDto;
import de.supercode.superBnB.dtos.PropertySaveResponseDto;
import de.supercode.superBnB.entities.Address;
import de.supercode.superBnB.entities.Property;
import de.supercode.superBnB.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // Implement CRUD operations for Property
    public PropertySaveResponseDto saveNewProperty(PropertySaveRequestDto dto) {
        Address address = new Address(dto.street(), dto.houseNumber(), dto.zipCode(), dto.city());
        Property newProperty = new Property(dto.maxNumGuests(), dto.pricePerNight(), address);
        address.setProperty(newProperty);
        propertyRepository.save(newProperty);
        return new PropertySaveResponseDto(newProperty.getId(), address.getStreet(), address.getHouseNumber(), address.getZipCode(), address.getCity(), newProperty.getMaxNumGuests(), dto.pricePerNight());
    }
}
