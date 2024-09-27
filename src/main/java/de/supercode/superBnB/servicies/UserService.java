package de.supercode.superBnB.servicies;

import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.exeptions.UserNotFoundException;
import de.supercode.superBnB.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id). orElseThrow(() -> new UserNotFoundException(String.format("User with id: %s not found", id)));
    }

    public User findUserByEmail(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("User with username: %s not found", username)));
    }
}
