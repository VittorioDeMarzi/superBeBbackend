package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.BookingRequestDto;
import de.supercode.superBnB.dtos.BookingResponseDto;
import de.supercode.superBnB.servicies.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingControllerTest {
    // STRUKTUR
    // Triple A
    // (1) Arrange (Vorbereitung)
    // (2) Act (Ausführung)
    // (3) Assert (Überprüfung)

    @Mock
    private static BookingService mockPropertyservice;

    private static BookingRequestDto exampleBookingRequestDto;
    private static BookingResponseDto exampleBookingResponseDto;


    @BeforeEach
    void setUp() {
        exampleBookingRequestDto = new BookingRequestDto(
                1L,
                3,
                2,
                LocalDate.of(2025, 5,25),
                LocalDate.of(2025, 6,25)
        );

/*        exampleBookingResponseDto = new BookingRequestDto(
                1L,
                3,
                2,
                LocalDate.of(2025, 5,25),
        LocalDate.of(2025, 6,25))
        ;*/

    }

    @Test
    void createBooking() {
    }

    @Test
    void getAllPersonalBookings() {
    }

    @Test
    void getAllUserBookings() {
    }
}