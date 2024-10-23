package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.ChangeRoleRequestDto;
import de.supercode.superBnB.dtos.UserFirstRegResponseDto;
import de.supercode.superBnB.servicies.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/change-role")
    public ResponseEntity<UserFirstRegResponseDto> changeUserRole(@PathVariable long userId, @RequestBody @Validated ChangeRoleRequestDto dto, Authentication authentication){

        return ResponseEntity.ok(userService.updateRole(userId, dto));
    }
}
