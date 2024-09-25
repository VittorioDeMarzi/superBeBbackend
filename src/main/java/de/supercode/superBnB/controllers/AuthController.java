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

//    AuthenticationService authenticationService;
//
//    public AuthController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }
//
//    @PostMapping("/signin")
//    public void signin(){}
//
//    @PostMapping("/signup")
//    public UserResponseDto signup(@RequestBody @Validated UserRegistrationDto dto){
//        return authenticationService.signUp(dto);
//    }
//
//    @GetMapping("/logout")
//    public void logout(HttpSession session){
//        session.invalidate();
//    }

    TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication){
        String token = tokenService.generateToken(authentication);
        System.out.println("Token erstellt für " + authentication.getName());
        System.out.println("Token: " + token);
        return token;
    }
}
