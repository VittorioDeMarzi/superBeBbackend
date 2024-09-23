package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.BookingRequestDto;
import de.supercode.superBnB.dtos.BookingResponseDto;
import de.supercode.superBnB.entities.User;
import de.supercode.superBnB.servicies.BookingService;
import de.supercode.superBnB.servicies.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/superbeb/booking")
public class BookingController {

    private BookingService bookingService;
    private UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingDto, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        BookingResponseDto createdBookingDto = bookingService.makeNewBooking(bookingDto, user);
        return new ResponseEntity<>(createdBookingDto, HttpStatus.CREATED);
    }

    // Weitere Endpunkte (z.B. für GET und DELETE)

}
