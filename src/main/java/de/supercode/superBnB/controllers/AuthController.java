package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.JwtDto;
import de.supercode.superBnB.dtos.UserRegistrationDto;
import de.supercode.superBnB.dtos.UserResponseDto;
import de.supercode.superBnB.servicies.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtDto> signin(Authentication authentication){
        return ResponseEntity.ok(new JwtDto(authenticationService.getJwt(authentication)));
    }

    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody @Validated UserRegistrationDto dto){
        return authenticationService.signUp(dto);
    }
}
