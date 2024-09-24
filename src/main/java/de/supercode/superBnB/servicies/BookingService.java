package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.BookingRequestDto;
import de.supercode.superBnB.dtos.BookingResponseDto;
import de.supercode.superBnB.entities.Booking;
import de.supercode.superBnB.entities.Property;
import de.supercode.superBnB.entities.User;
import de.supercode.superBnB.exeptions.InvalidBookingRequestException;
import de.supercode.superBnB.mappers.BookingDtoMapper;
import de.supercode.superBnB.repositories.BookingRepository;
import de.supercode.superBnB.repositories.PropertyRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookingService {

    BookingRepository bookRepository;
    PropertyRepository propertyRepository;
    BookingDtoMapper bookingDtoMapper;
    UserService userService;

    public BookingService(BookingRepository bookRepository, PropertyRepository propertyRepository, BookingDtoMapper bookingDtoMapper, UserService userService) {
        this.bookRepository = bookRepository;
        this.propertyRepository = propertyRepository;
        this.bookingDtoMapper = bookingDtoMapper;
        this.userService = userService;
    }

    public BookingResponseDto makeNewBooking(BookingRequestDto dto, User user) {
        if (dto.checkInDate().isAfter(dto.checkOutDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Property property = propertyRepository.findById(dto.propertyId()).orElseThrow(() -> new NoSuchElementException("Property with id [%s] not found".formatted(dto.propertyId())));
        List<Booking> existingBookings = property.getBookings();
        Boolean isAvailable = checkAvailabilityForDates(dto, existingBookings);
        if (!isAvailable) throw new InvalidBookingRequestException("Property is not available for the given dates");
        Booking newBooking = makeNewBookingFromDto(dto, user, property);
        property.addBooking(newBooking);
        bookRepository.save(newBooking);
        return bookingDtoMapper.apply(newBooking);
    }

    private Booking makeNewBookingFromDto(BookingRequestDto dto, User user, Property property) {
        Booking newBooking = new Booking();
        newBooking.setUser(user);
        newBooking.setProperty(property);
        newBooking.setNumAdults(dto.numAdults());
        newBooking.setNumChildren(dto.numChildren());
        newBooking.setCheckInDate(dto.checkInDate());
        newBooking.setCheckOutDate(dto.checkOutDate());
        long totalNights = ChronoUnit.DAYS.between(dto.checkInDate(), dto.checkOutDate());
        newBooking.setTotalPrice(property.getPricePerNight().multiply(BigDecimal.valueOf(totalNights)));
        return newBooking;
    }

    private Boolean checkAvailabilityForDates(BookingRequestDto dto, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                    existingBooking.getCheckInDate().isBefore(dto.checkOutDate()) && existingBooking.getCheckOutDate().isAfter(dto.checkInDate())
                );
    }

    public List<BookingResponseDto> getAllUserBookings(long id) {
        List<Booking> personalBookings = bookRepository.findByUserId(id).orElseThrow(() -> new NoSuchElementException(String.format("User with ID: %s does not have any booking yet", id)));

        return personalBookings.stream()
                .map(bookingDtoMapper)
                .collect(Collectors.toList());
    }
}
