package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.*;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.user.Role;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.entities.user.UserProfile;
import de.supercode.superBnB.exeptions.UserNotFoundException;
import de.supercode.superBnB.mappers.AddressDtoMapper;
import de.supercode.superBnB.mappers.UserDtoMapper;
import de.supercode.superBnB.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    UserRepository userRepository;
    AddressService addressService;

    public UserService(UserRepository userRepository, AddressService addressService) {
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id). orElseThrow(() -> new UserNotFoundException(String.format("User with id: %s not found", id)));
    }

    public User findUserByEmail(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("User with username: %s not found", username)));
    }

    // Allowed just for ADMIN users
    public UserFirstRegResponseDto updateRole(long userId, ChangeRoleRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User with id: " + userId + " not found"));

        user.setRole(Role.valueOf(dto.newRole()));
        return new UserFirstRegResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().name());
    }

    protected boolean userProfileIsComplete(User user) {
        UserProfile userProfile = user.getUserProfile();
        if (userProfile == null) return false;

        Address address = userProfile.getAddress();

        return address.getStreet() != null && !address.getStreet().isEmpty()
                && address.getCity() != null && !address.getCity().isEmpty()
                && address.getZipCode() != null && !address.getZipCode().isEmpty()
                && userProfile.getPhoneNumber() != null && !userProfile.getPhoneNumber().isEmpty()
                && userProfile.getFirstName() != null && !userProfile.getFirstName().isEmpty()
                && userProfile.getLastName() != null && !userProfile.getLastName().isEmpty()
                && userProfile.getDateOfBirth() != null;
    }

    public UserResponseDto getUserProfile(Authentication authentication) {
        User user = findUserByEmail(authentication.getName());
        return UserDtoMapper.mapToDto(user);
    }

    public UserShortDto updateProfile(Authentication authentication, UserProfileDto dto) {
        User user = findUserByEmail(authentication.getName());
        UserProfile userProfile = user.getUserProfile();

        if (userProfile == null) {
            userProfile = new UserProfile();
            user.setUserProfile(userProfile);
        }
        if (dto.firstName() != null && !dto.firstName().isEmpty()) userProfile.setFirstName(dto.firstName());
        if (dto.lastName() != null && !dto.lastName().isEmpty()) userProfile.setLastName(dto.lastName());
        if (dto.dateOfBirth() != null) userProfile.setDateOfBirth(dto.dateOfBirth());
        if (dto.phoneNumber() != null && !dto.phoneNumber().isEmpty()) userProfile.setPhoneNumber(dto.phoneNumber());

        userRepository.save(user);

        return UserDtoMapper.mapToShortDto(user);
    }

    public AddressDto updateAddress(Authentication authentication, AddressDto dto) {
        User user = findUserByEmail(authentication.getName());

        UserProfile userProfile = user.getUserProfile();
        if (userProfile == null) {
            userProfile = new UserProfile();
            user.setUserProfile(userProfile);
        }

        Address address = addressService.saveNewAddressIfDoesNotExist(dto);
        userProfile.setAddress(address);

        userRepository.save(user);

        return AddressDtoMapper.mapToDto(address);
    }
}
