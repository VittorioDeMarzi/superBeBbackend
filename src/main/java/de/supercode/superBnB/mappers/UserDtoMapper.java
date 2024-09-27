package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.UserResponseDto;
import de.supercode.superBnB.entities.user.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserResponseDto> {
    @Override
    public UserResponseDto apply(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getRole(),
                user.getUsername(),
                user.getUserProfile().getFirstName(),
                user.getUserProfile().getLastName(),
                user.getUserProfile().getDateOfBirth(),
                user.getUserProfile().getPhoneNumber(),
                user.getUserProfile().getAddress().getStreet(),
                user.getUserProfile().getAddress().getHouseNumber(),
                user.getUserProfile().getAddress().getZipCode(),
                user.getUserProfile().getAddress().getCity(),
                user.getUserProfile().getAddress().getCountry()
        );
    }
}
