package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.ChangeRoleRequestDto;
import de.supercode.superBnB.dtos.UserFirstRegResponseDto;
import de.supercode.superBnB.servicies.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AdminControllerTest {
    // STRUKTUR
    // Triple A
    // (1) Arrange (Vorbereitung)
    // (2) Act (Ausführung)
    // (3) Assert (Überprüfung)

    @Mock
    private static UserService mockUserService;

    @InjectMocks
    private AdminController adminController;

    private static ChangeRoleRequestDto exampleChangeRequest;
    private static UserFirstRegResponseDto exampleUserResponse;

    @BeforeAll
    public static void setUp() {
        exampleChangeRequest = new ChangeRoleRequestDto("admin");
        exampleUserResponse = new UserFirstRegResponseDto(2L, "fakeUser@test.it", "admin");
    }

/*    @Test
    public void testChangeRole() {
        // (1) Arrange
        when(mockUserService.updateRole(2L, exampleChangeRequest)).thenReturn(exampleUserResponse);
        // (2) Act
        ResponseEntity<UserFirstRegResponseDto> result = adminController.changeUserRole(2L, exampleChangeRequest)
        // (3) Assert
    }*/

}