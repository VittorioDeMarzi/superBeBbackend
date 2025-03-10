package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.BookingRequestDto;
import de.supercode.superBnB.dtos.BookingResponseDto;
import de.supercode.superBnB.entities.booking.Booking;
import de.supercode.superBnB.entities.booking.GenerateBookingsNumber;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.entities.booking.SeasonalPrice;
import de.supercode.superBnB.entities.user.User;
import de.supercode.superBnB.entities.user.UserProfile;
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
    UserService userService;
    SeasonalPriceService seasonalPriceService;
    GenerateBookingsNumber generateBookingsNumber;
    MailService mailService;

    public BookingService(BookingRepository bookRepository, PropertyService propertyService, UserService userService, SeasonalPriceService seasonalPriceService, GenerateBookingsNumber generateBookingsNumber, MailService mailService) {
        this.bookRepository = bookRepository;
        this.propertyService = propertyService;
        this.userService = userService;
        this.seasonalPriceService = seasonalPriceService;
        this.generateBookingsNumber = generateBookingsNumber;
        this.mailService = mailService;
    }

    // Creates a new booking, ensuring dates are valid and property is available
    @Transactional
    public BookingResponseDto makeNewBooking(BookingRequestDto dto, User user) {

        if(!userService.userProfileIsComplete(user)) throw new InvalidBookingRequestException("User's Profile is not complete");
        Property property = propertyService.findPropertyById(dto.propertyId());
        if (property.getMaxNumGuests() < (dto.numChildren()+ dto.numAdults())) throw new IllegalArgumentException("Max number of guest exceeded");
        if(!propertyService.checkAvailabilityForDates(dto.checkInDate(), dto.checkOutDate(), property)) throw new InvalidBookingRequestException("Property is not available for the given dates");
        Booking newBooking = makeNewBookingFromDto(dto, user, property);
        property.addBooking(newBooking);
        bookRepository.save(newBooking);
        BookingResponseDto bookingResponseDto = BookingDtoMapper.mapToDto(newBooking);
//        mailService.sendBookingConfirmationEmail(user.getUsername(), bookingResponseDto);

        return bookingResponseDto;
    }

    // Helper method to create a new Booking object from the booking request DTO
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

    BigDecimal calculateTotalPrice(Long propertyId, LocalDate checkInDate, LocalDate checkOutDate) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalDate currentDate = checkInDate;

        while(!currentDate.isEqual(checkOutDate)) {
            Property property = propertyService.findPropertyById(propertyId);
            Optional<SeasonalPrice> currentSeasonalPrice = seasonalPriceService.getSeasonPriceForCurrentDate(propertyId, currentDate);
            if (currentSeasonalPrice.isPresent()) totalPrice = totalPrice.add(currentSeasonalPrice.get().getPricePerNight()); // Get the seasonal price for the current date
            else totalPrice = totalPrice.add(property.getMinPricePerNight());
            currentDate = currentDate.plusDays(1);
        }
        return totalPrice;
    }

    public List<BookingResponseDto> getAllUserBookings(long id) {
        List<Booking> personalBookings = bookRepository.findByUserId(id).orElseThrow(() -> new NoSuchElementException(String.format("User with ID: %s does not have any booking yet", id)));

        return personalBookings.stream()
                .map(BookingDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
