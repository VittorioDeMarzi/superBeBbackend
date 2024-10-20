package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.*;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.user.Role;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.entities.user.UserProfile;
import de.supercode.superBnB.mappers.UserDtoMapper;
import de.supercode.superBnB.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AddressService addressService;
    UserDtoMapper userDtoMapper;
    TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AddressService addressService, UserDtoMapper userDtoMapper, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
        this.userDtoMapper = userDtoMapper;
        this.tokenService = tokenService;

    }

    public UserFirstRegResponseDto signUp(UserFirstRegistrationDto dto) {
//        AddressSaveDto addressDto = new AddressSaveDto(
//                dto.street(),
//                dto.houseNumber(),
//                dto.zipCode(),
//                dto.city(),
//                dto.country());
//        Address address = addressService.saveNewAddressIfDoesNotExist(addressDto);
//        UserProfile userProfile = new UserProfile(
//                dto.firstName(),
//                dto.lastName(),
//                dto.dateOfBirth(),
//                dto.phoneNumber(),
//                address);
        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.valueOf("ADMIN"));
//        user.setUserDetails(userProfile);

//        return userDtoMapper.apply(userRepository.save(user));
        userRepository.save(user);
        return new UserFirstRegResponseDto(
                user.getUsername(),
                user.getPassword()
        );
    }

    public String getJwt(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
