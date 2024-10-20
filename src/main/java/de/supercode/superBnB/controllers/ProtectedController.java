package de.supercode.superBnB.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ProtectedController {

    @GetMapping("/user")
    public ResponseEntity<String> getUser(Authentication authentication){
        return ResponseEntity.ok(authentication.getName());
    }
}
