package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.UserResponseDto;
import de.supercode.superBnB.dtos.UserShortDto;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.entities.user.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class UserDtoMapper{
    public static UserResponseDto mapToDto(User user) {
        UserProfile userProfile = user.getUserProfile();
        Address address = (userProfile != null) ? userProfile.getAddress() : null;

        return new UserResponseDto(
                user.getId(),
                user.getRole(),
                user.getUsername(),
                Optional.ofNullable(userProfile).map(UserProfile::getFirstName).orElse(""),
                Optional.ofNullable(userProfile).map(UserProfile::getLastName).orElse(""),
                Optional.ofNullable(userProfile).map(UserProfile::getDateOfBirth).orElse(null),
                Optional.ofNullable(userProfile).map(UserProfile::getPhoneNumber).orElse(""),
                Optional.ofNullable(address).map(Address::getStreet).orElse(""),
                Optional.ofNullable(address).map(Address::getHouseNumber).orElse(""),
                Optional.ofNullable(address).map(Address::getZipCode).orElse(""),
                Optional.ofNullable(address).map(Address::getCity).orElse(""),
                Optional.ofNullable(address).map(Address::getCountry).orElse("")
        );
    }


    public static UserShortDto mapToShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getUserProfile().getFirstName(),
                user.getUserProfile().getLastName(),
                user.getUserProfile().getDateOfBirth(),
                user.getUserProfile().getPhoneNumber()
        );
    }

}
