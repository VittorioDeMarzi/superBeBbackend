package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.AddressDto;
import de.supercode.superBnB.dtos.UserProfileDto;
import de.supercode.superBnB.dtos.UserProfileAddressDto;
import de.supercode.superBnB.dtos.UserShortDto;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.entities.user.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDtoMapper{
    public static UserProfileAddressDto mapToDto(User user) {
        UserProfile userProfile = user.getUserProfile();
        Address address = (userProfile != null) ? userProfile.getAddress() : null;

        AddressDto addressDto = new AddressDto(
                Optional.ofNullable(address).map(Address::getStreet).orElse(""),
                Optional.ofNullable(address).map(Address::getHouseNumber).orElse(""),
                Optional.ofNullable(address).map(Address::getZipCode).orElse(""),
                Optional.ofNullable(address).map(Address::getCity).orElse(""),
                Optional.ofNullable(address).map(Address::getCountry).orElse("")
        );
        UserProfileDto userProfileDto = new UserProfileDto(
                user.getId(),
                user.getUsername(),
                Optional.ofNullable(userProfile).map(UserProfile::getFirstName).orElse(""),
                Optional.ofNullable(userProfile).map(UserProfile::getLastName).orElse(""),
                Optional.ofNullable(userProfile).map(UserProfile::getDateOfBirth).orElse(null),
                Optional.ofNullable(userProfile).map(UserProfile::getPhoneNumber).orElse("")
        );
        return new UserProfileAddressDto(

                userProfileDto,
                addressDto

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
