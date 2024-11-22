package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.*;
import de.supercode.superBnB.servicies.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // USER - get profile
    @GetMapping
    public ResponseEntity<UserResponseDto> getUserProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserProfile(authentication));
    }

    // USER - update Address
    @PutMapping
    public ResponseEntity<UserShortDto> updateUserProfile(Authentication authentication, @RequestBody UserProfileDto dto) {
        return ResponseEntity.ok(userService.updateProfile(authentication, dto));
    }


    // USER - update Address
    @PutMapping("/address")
    public ResponseEntity<AddressDto> updateAddress(Authentication authentication, @RequestBody @Validated AddressDto dto) {
        return ResponseEntity.ok(userService.updateAddress(authentication, dto));
    }
}
