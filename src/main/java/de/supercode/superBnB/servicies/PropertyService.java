package de.supercode.superBnB.servicies;

import de.supercode.superBnB.dtos.*;
import de.supercode.superBnB.entities.booking.Booking;
import de.supercode.superBnB.entities.property.Address;
import de.supercode.superBnB.entities.property.Property;
import de.supercode.superBnB.exeptions.InvalidBookingRequestException;
import de.supercode.superBnB.mappers.PropertyDtoMapper;
import de.supercode.superBnB.repositories.PropertyRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;
    PropertyDtoMapper propertyDtoMapper;
    AddressService addressService;
    BookingService bookingService;

    public PropertyService(PropertyRepository propertyRepository, PropertyDtoMapper propertyDtoMapper, AddressService addressService, @Lazy BookingService bookingService) {
        this.propertyRepository = propertyRepository;
        this.propertyDtoMapper = propertyDtoMapper;
        this.addressService = addressService;
        this.bookingService = bookingService;
    }

    // Implement CRUD operations for Property
    public PropertyResponseDto saveNewProperty(PropertyRequestDto dto) {
        if (dto == null) throw new NullPointerException("dto must not be null");
        AddressSaveDto addressDto = new AddressSaveDto(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city(),
                dto.country()
        );
        Address address = addressService.saveNewAddressIfDoesNotExist(addressDto);
        Property newProperty = createNewPropertyFromDto(dto, address);
        propertyRepository.save(newProperty);
        return propertyDtoMapper.apply(newProperty);
    }

    private Property createNewPropertyFromDto(PropertyRequestDto dto, Address address) {
        return new Property(
                dto.title(),
                dto.description(),
                dto.maxNumGuests(),
                dto.minPricePerNight(),
                address,
                dto.rooms()
        );
    }


    public void saveNewImage(long propertyId, String imageUrl) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NoSuchElementException("Property not found: " + propertyId));
        property.getPicUrls().add(imageUrl);
        propertyRepository.save(property);
    }

    public Property findPropertyById(Long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Property with id [%s] not found".formatted(id)));
    }

    public PropertyResponseDto findPropertyDtoById(Long id) {
        if (id == null) throw new NullPointerException("id must not be null");
       Property property = propertyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Property with id [%s] not found".formatted(id)));
       return propertyDtoMapper.apply(property);
    }

    public List<PropertyResponseDto> getAllProperties() {
        List<Property> allProperties = propertyRepository.findAll();
        allProperties.forEach(prop -> System.out.println(prop.getId()));
        return allProperties.stream()
                .map(propertyDtoMapper)
                .collect(Collectors.toList());
    }

    public List<PropertyResponseDto> getAllPublicProperties() {
        List<Property> allPublicProperties = propertyRepository.findByIsPublic(true);
        return allPublicProperties.stream()
                .map(propertyDtoMapper)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if(!propertyRepository.existsById(id)) throw new NoSuchElementException("Property not found with id: " + id);
        propertyRepository.deleteById(id);
    }

    public PropertyResponseDto updateProperty(Long id, PropertyRequestDto dto) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Property not found with id: " + id));

        AddressSaveDto addressDto = new AddressSaveDto(
                dto.street(),
                dto.houseNumber(),
                dto.zipCode(),
                dto.city(),
                dto.country());
        Address newAddress = addressService.saveNewAddressIfDoesNotExist(addressDto);

        property.setTitle(dto.title());
        property.setDescription(dto.description());
        property.setMaxNumGuests(dto.maxNumGuests());
        property.setRooms(dto.rooms());
        property.setAddress(newAddress);
        property.setMinPricePerNight(dto.minPricePerNight());
        propertyRepository.save(property);
        return propertyDtoMapper.apply(property);
    }

    public PropertyResponseDto changeVisibility(long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NoSuchElementException("Property not found with id: " + propertyId));

        if (property.getPicUrls().isEmpty()) {
            throw new IllegalArgumentException("Property does not have any image yet. Change visibility not allowed");
        }
            property.setPublic(!property.isPublic());
            propertyRepository.save(property);
            return propertyDtoMapper.apply(property);
    }

    public RequestPriceAndAvailabilityResponseDto checkAvailabilityAndPrice(Long propertyId, BookingRequestDto dto) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NoSuchElementException("Property not found with id: " + propertyId));
        if(!property.isPublic()) throw new IllegalArgumentException("Property not public");
        if (property.getMaxNumGuests() < (dto.numChildren()+ dto.numAdults())) throw new IllegalArgumentException("Max number of guest exceeded");
        LocalDate startDate = dto.checkInDate();
        LocalDate endDate = dto.checkOutDate();
        Boolean isAvailable = checkAvailabilityForDates(startDate, endDate, property);

        if (!isAvailable) {
            return new RequestPriceAndAvailabilityResponseDto(false, null, null, 0);
        }
        BigDecimal totalPrice = bookingService.calculateTotalPrice(propertyId, startDate, endDate);
        long numNight = ChronoUnit.DAYS.between(dto.checkInDate(), dto.checkOutDate());
        BigDecimal pricePerNight = totalPrice.divide(BigDecimal.valueOf(numNight), 2, RoundingMode.HALF_UP);

        return new RequestPriceAndAvailabilityResponseDto(true, totalPrice, pricePerNight, numNight);
    }

    private Boolean checkAvailabilityForDates(LocalDate startDate, LocalDate endDate, Property property) {
        if (startDate.isAfter(endDate)) throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        List<Booking> existingBookings = property.getBookings();
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        existingBooking.getCheckInDate().isBefore(endDate) && existingBooking.getCheckOutDate().isAfter(startDate)
                );
    }


}
