package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.BookingResponseDto;
import de.supercode.superBnB.dtos.UserShortDto;
import de.supercode.superBnB.entities.booking.Booking;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@Service
public class BookingDtoMapper {


    public static BookingResponseDto mapToDto(Booking booking) {

        return new BookingResponseDto(
                booking.getId(),
                UserDtoMapper.mapToShortDto(booking.getUser()),
                booking.getBookingCode(),
                booking.getBookingDate(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getNumAdults(),
                booking.getNumChildren(),
                booking.getTotNumGuests(),
                ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate()),
                booking.getTotalPrice(),
                PropertyDtoMapper.mapToDto(booking.getProperty())
        );
    }
}
