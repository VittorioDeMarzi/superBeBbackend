package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.UserRegistrationDto;
import de.supercode.superBnB.dtos.UserResponseDto;
import de.supercode.superBnB.servicies.AuthenticationService;
import jakarta.servlet.http.HttpSession;
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
    public void signin(){}

    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody @Validated UserRegistrationDto dto){
        return authenticationService.signUp(dto);
    }

    @GetMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }
}
