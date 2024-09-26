package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.UserRegistrationDto;
import de.supercode.superBnB.dtos.UserResponseDto;
import de.supercode.superBnB.servicies.AuthenticationService;
import de.supercode.superBnB.servicies.TokenService;
import jakarta.servlet.http.HttpSession;
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
    public String signin(Authentication authentication){
        return authenticationService.token(authentication);
    }

    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody @Validated UserRegistrationDto dto){
        return authenticationService.signUp(dto);
    }
}
