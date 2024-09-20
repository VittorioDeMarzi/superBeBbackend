package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.AuthDto;
import de.supercode.superBnB.entities.Role;
import de.supercode.superBnB.entities.User;
import de.supercode.superBnB.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthentificationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(AuthDto dto) {
        User user = new User();
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));

        return userRepository.save(user);
    }
}
