package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.BookingRequestDto;
import de.supercode.superBnB.dtos.BookingResponseDto;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.servicies.BookingService;
import de.supercode.superBnB.servicies.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superbeb/booking")
public class BookingController {

    BookingService bookingService;
    UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    // Endpoint to create a new booking
    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody @Validated BookingRequestDto bookingDto, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        BookingResponseDto createdBookingDto = bookingService.makeNewBooking(bookingDto, user);
        return new ResponseEntity<>(createdBookingDto, HttpStatus.CREATED);
    }

    // Endpoint to retrieve all personal bookings for the authenticated user
    @GetMapping("/personalBookings")
    public ResponseEntity<List<BookingResponseDto>> getAllPersonalBookings(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findUserByEmail(username);
        List<BookingResponseDto> bookingResponseDtos = bookingService.getAllUserBookings(user.getId());

        return ResponseEntity.ok(bookingResponseDtos);
    }

    // Endpoint to retrieve all bookings for a specific user, accessible only by admin
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/userBookings/{id}")
    public ResponseEntity<List<BookingResponseDto>> getAllUserBookings(@PathVariable long id) {
        List<BookingResponseDto> bookingResponseDtos = bookingService.getAllUserBookings(id);

        return ResponseEntity.ok(bookingResponseDtos);
    }
}
