package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.*;
import de.supercode.superBnB.servicies.PropertyService;
import de.supercode.superBnB.servicies.SeasonalPriceService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/superbeb/property")
public class PropertyController {
    private PropertyService propertyService;
    private SeasonalPriceService seasonalPriceService;

    public PropertyController(PropertyService propertyService, SeasonalPriceService SeasonalPriceService) {
        this.propertyService = propertyService;
        this.seasonalPriceService = SeasonalPriceService;
    }

    // Save a new property, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<PropertyResponseDto> saveNewProperty(
            @RequestBody @Validated PropertyRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveNewProperty(dto));
    }

    // Save more properties, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("save-properties")
    public ResponseEntity<List<PropertyResponseDto>> saveLoadsNewProperties(
            @RequestBody @Validated PropertyRequestDto[] dtos) {
        List<PropertyResponseDto> savedProperties = Arrays.stream(dtos)
                .map(dto -> propertyService.saveNewProperty(dto))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperties);
    }

    // Retrieve all properties, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
    }

    // ALL - Retrieve all public properties
    @GetMapping("/public")
    public ResponseEntity<List<PropertyResponseDto>> getAllPublicProperties(
            @RequestParam (defaultValue = "8", required = false) int noElements,
            @RequestParam(defaultValue = "0", required = false) int page) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllPublicProperties(noElements, page));
    }

    // ALL - Retrieve all public properties with filters
    @GetMapping("/public/filtered")
    public ResponseEntity<Page<PropertyResponseDto>> getAllPublicPropertiesFiltered(
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "checkInDate", required = false) LocalDate checkInDate,
            @RequestParam(value = "checkOutDate", required = false) LocalDate checkOutDate,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "numGuests", required = false) Integer guests,
            @RequestParam(value = "minRooms", required = false) Integer rooms,

            @RequestParam(value = "numElements", required= false) int numElements,
            @RequestParam(value = "page", required= false) int page
    ) {
        Page<PropertyResponseDto> properties = propertyService.getAllPublicPropertiesWithFiltering(city, checkInDate, checkOutDate, minPrice, maxPrice, guests, rooms, numElements, page);
        return ResponseEntity.status(HttpStatus.OK).body(properties);
    }

    // Retrieve a specific property by ID
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.findPropertyDtoById(id));
    }

    // Delete a property by ID, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProperty(
            @PathVariable Long id) {
        propertyService.deleteById(id);
        return ResponseEntity.ok("Property was deleted successfully");
    }

    // Update an existing property by ID, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(
            @PathVariable Long id,
            @RequestBody PropertyRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.updateProperty(id, dto));
    }

    // Add seasonal pricing for a specific property, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/addSeasonalPrice/{id}")
    public ResponseEntity<SeasonalPriceResponseDto> addSeasonalPrice(
            @PathVariable Long id, @RequestBody SeasonalPriceRequestDto dto) {
        return ResponseEntity.ok(seasonalPriceService.addSeasonalPrice(id, dto));
    }

    // Retrieve all seasonal prices for a specific property, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/seasonalPrices/{id}")
    public ResponseEntity<List<SeasonalPriceResponseDto>> getAllSeasonalPrices(
            @PathVariable Long id) {
        return ResponseEntity.ok(seasonalPriceService.getAllSeasonalPricesByProperty(id));
    }

    // Change the visibility of a property, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/change-visibility/{propertyId}")
    public ResponseEntity<PropertyResponseDto> changeVisibility(
            @PathVariable long propertyId) {

        return ResponseEntity.ok(propertyService.changeVisibility(propertyId));
    }

    // Check availability and price, PUBLIC
    @PostMapping("/public/check-availability")
    public ResponseEntity<RequestPriceAndAvailabilityResponseDto> checkAvailabilityAndPrice(
            @RequestBody BookingRequestDto dto) {
        return ResponseEntity.ok(propertyService.checkAvailabilityAndPrice(dto));
    }

    // Get all cities, PUBLIC
    @GetMapping("/public/cities")
    public ResponseEntity<List<String>> getAllCities() {
        return ResponseEntity.ok(propertyService.getAllCities());
    }
}
