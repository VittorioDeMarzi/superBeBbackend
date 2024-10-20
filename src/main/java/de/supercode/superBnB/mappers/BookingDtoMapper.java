package de.supercode.superBnB.mappers;

import de.supercode.superBnB.dtos.BookingResponseDto;
import de.supercode.superBnB.entities.booking.Booking;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@Service
public class BookingDtoMapper implements Function<Booking, BookingResponseDto> {

    PropertyDtoMapper propertyDtoMapper;

    public BookingDtoMapper(PropertyDtoMapper propertyDtoMapper) {
        this.propertyDtoMapper = propertyDtoMapper;
    }

    @Override
    public BookingResponseDto apply(Booking booking) {
        return new BookingResponseDto(
                booking.getId(),
                booking.getUser().getId(),
                booking.getBookingCode(),
                booking.getBookingDate(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                booking.getNumAdults(),
                booking.getNumChildren(),
                booking.getTotNumGuests(),
                ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate()),
                booking.getTotalPrice(),
                propertyDtoMapper.apply(booking.getProperty())
        );
    }
}
