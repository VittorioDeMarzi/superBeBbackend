package de.supercode.superBnB.repositories;

import de.supercode.superBnB.entities.property.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByStreetAndHouseNumberAndZipCodeAndCity (String street, String houseNumber, String zipCode, String city);
}
