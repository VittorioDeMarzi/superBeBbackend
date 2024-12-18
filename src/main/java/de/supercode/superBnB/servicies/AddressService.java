package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.AddressDto;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveNewAddressIfDoesNotExist(AddressDto dto) {
        // Attempts to find an existing address by street, house number, zip code, and city
        return addressRepository.findByStreetAndHouseNumberAndZipCodeAndCity(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city()
        // If not found, create a new Address object
        ).orElseGet(() -> new Address(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city(),
                dto.country())
        );
    }
}
