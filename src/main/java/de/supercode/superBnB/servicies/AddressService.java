package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.AddressSaveDto;
import de.supercode.superBnB.entities.Address;
import de.supercode.superBnB.repositories.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address saveNewAddressIfDoesNotExist(AddressSaveDto dto) {
        return addressRepository.findByStreetAndHouseNumberAndZipCodeAndCity(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city()
        ).orElseGet(() -> new Address(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city(),
                dto.country())
        );
    }
}
