package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.BookingRequestDto;
import de.supercode.superBnB.dtos.BookingResponseDto;
import de.supercode.superBnB.entities.booking.Booking;
import de.supercode.superBnB.entities.booking.GenerateBookingsNumber;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.entities.booking.SeasonalPrice;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.exeptions.InvalidBookingRequestException;
import de.supercode.superBnB.mappers.BookingDtoMapper;
import de.supercode.superBnB.repositories.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    BookingRepository bookRepository;
    PropertyService propertyService;
    BookingDtoMapper bookingDtoMapper;
    UserService userService;
    SeasonalPriceService seasonalPriceService;
    GenerateBookingsNumber generateBookingsNumber;

    public BookingService(BookingRepository bookRepository, PropertyService propertyService, BookingDtoMapper bookingDtoMapper, UserService userService, SeasonalPriceService seasonalPriceService, GenerateBookingsNumber generateBookingsNumber) {
        this.bookRepository = bookRepository;
        this.propertyService = propertyService;
        this.bookingDtoMapper = bookingDtoMapper;
        this.userService = userService;
        this.seasonalPriceService = seasonalPriceService;
        this.generateBookingsNumber = generateBookingsNumber;
    }

    @Transactional
    public BookingResponseDto makeNewBooking(BookingRequestDto dto, User user) {
        if (dto.checkInDate().isAfter(dto.checkOutDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
        Property property = propertyService.findPropertyById(dto.propertyId());
        checkAvailabilityForDates(dto, property);

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
        newBooking.setBookingCode(generateBookingsNumber.generateUniqueRandomCode());
        long totalNights = ChronoUnit.DAYS.between(dto.checkInDate(), dto.checkOutDate());
        BigDecimal totalPrice = calculateTotalPrice(dto.propertyId(), dto.checkInDate(), dto.checkOutDate());
        newBooking.setTotalPrice(totalPrice);
        return newBooking;
    }

    private BigDecimal calculateTotalPrice(long propertyId, LocalDate checkInDate, LocalDate checkOutDate) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalDate currentDate = checkInDate;

        while(!currentDate.isEqual(checkOutDate)) {
            Property property = propertyService.findPropertyById(propertyId);
            Optional<SeasonalPrice> currentSeasonalPrice = seasonalPriceService.getSeasonPriceForCurrentDate(propertyId, currentDate);
            if (currentSeasonalPrice.isPresent()) totalPrice = totalPrice.add(currentSeasonalPrice.get().getPricePerNight());
            else totalPrice = totalPrice.add(property.getMinPricePerNight());
            currentDate = currentDate.plusDays(1);
        }
        return totalPrice;
    }

    private void checkAvailabilityForDates(BookingRequestDto dto, Property property) {
        List<Booking> existingBookings = property.getBookings();
        boolean isAvailable = existingBookings.stream()
                .noneMatch(existingBooking ->
                    existingBooking.getCheckInDate().isBefore(dto.checkOutDate()) && existingBooking.getCheckOutDate().isAfter(dto.checkInDate())
                );
        if (!isAvailable) throw new InvalidBookingRequestException("Property is not available for the given dates");
    }

    public List<BookingResponseDto> getAllUserBookings(long id) {
        List<Booking> personalBookings = bookRepository.findByUserId(id).orElseThrow(() -> new NoSuchElementException(String.format("User with ID: %s does not have any booking yet", id)));

        return personalBookings.stream()
                .map(bookingDtoMapper)
                .collect(Collectors.toList());
    }
}
