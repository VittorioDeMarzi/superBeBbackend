package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.AuthDto;
import de.supercode.superBnB.entities.User;
import de.supercode.superBnB.servicies.AuthentificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthentificationService authentificationService;

    @PostMapping("/signin")
    public void signin(){}

    @PostMapping("/signup")
    public User signup(@RequestBody AuthDto dto){

        try {
            // Logica di signup
            return authentificationService.signUp(dto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during signup");
        }
    }

    @GetMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }
}
