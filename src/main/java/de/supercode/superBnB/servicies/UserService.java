package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.ChangeRoleRequestDto;
import de.supercode.superBnB.dtos.UserFirstRegResponseDto;
import de.supercode.superBnB.dtos.UserResponseDto;
import de.supercode.superBnB.entities.user.Role;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.exeptions.UserNotFoundException;
import de.supercode.superBnB.mappers.UserDtoMapper;
import de.supercode.superBnB.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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

    // Allowed just for ADMIN users
    public UserFirstRegResponseDto updateRole(long userId, ChangeRoleRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User with id: " + userId + " not found"));

        user.setRole(Role.valueOf(dto.newRole()));
        return new UserFirstRegResponseDto(
                user.getId(),
                user.getUsername(),
                user.getRole().name());
    }
}
