package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.*;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.servicies.AuthenticationService;
import de.supercode.superBnB.servicies.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    AuthenticationService authenticationService;
    UserService userService;

    public AuthController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtDto> signin(Authentication authentication){
        User user = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok(new JwtDto(authenticationService.getJwt(authentication), user.getUsername(), "ADMIN"));
    }

    @PostMapping("/signup")
    public UserFirstRegResponseDto signup(@RequestBody @Validated UserFirstRegistrationDto dto){
        return authenticationService.signUp(dto);
    }

}
