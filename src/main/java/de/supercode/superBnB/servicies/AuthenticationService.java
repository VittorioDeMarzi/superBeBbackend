package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.AddressSaveDto;
import de.supercode.superBnB.dtos.UserRegistrationDto;
import de.supercode.superBnB.dtos.UserResponseDto;
import de.supercode.superBnB.entities.Address;
import de.supercode.superBnB.entities.User;
import de.supercode.superBnB.entities.UserProfile;
import de.supercode.superBnB.mappers.UserDtoMapper;
import de.supercode.superBnB.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AddressService addressService;
    UserDtoMapper userDtoMapper;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressService addressService, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
        this.userDtoMapper = userDtoMapper;
    }

    public UserResponseDto signUp(UserRegistrationDto dto) {
        AddressSaveDto addressDto = new AddressSaveDto(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city(),
                dto.country());
        Address address = addressService.saveNewAddressIfDoesNotExist(addressDto);
        UserProfile userProfile = new UserProfile(
                dto.firstName(),
                dto.lastName(),
                dto.dateOfBirth(),
                dto.phoneNumber(),
                address);
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setUserDetails(userProfile);

        return userDtoMapper.apply(userRepository.save(user));
    }
}
