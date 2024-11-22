package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.AddressDto;
import de.supercode.superBnB.entities.property.Address;

public class AddressDtoMapper {

    public static AddressDto mapToDto(Address address) {
        return new AddressDto(
                address.getStreet(),
                address.getHouseNumber(),
                address.getZipCode(),
                address.getCity(),
                address.getCountry()
        );
    }
}
