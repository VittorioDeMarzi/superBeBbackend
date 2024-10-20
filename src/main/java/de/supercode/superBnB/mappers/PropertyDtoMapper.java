package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.property.Property;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PropertyDtoMapper implements Function<Property, PropertyResponseDto> {
    @Override
    public PropertyResponseDto apply(Property property) {
        Address address = property.getAddress();
        return new PropertyResponseDto(
                property.getId(),
                property.getTitle(),
                property.getDescription(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getZipCode(),
                address.getCity(),
                address.getCountry(),
                property.getRooms(),
                property.getMaxNumGuests(),
                property.getMinPricePerNight(),
                property.getPicUrls(),
                property.isPublic()
        );
    }
}
