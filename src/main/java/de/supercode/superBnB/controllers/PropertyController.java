package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.PropertyRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.dtos.SeasonalPriceRequestDto;
import de.supercode.superBnB.dtos.SeasonalPriceResponseDto;
import de.supercode.superBnB.entities.booking.SeasonalPrice;
import de.supercode.superBnB.servicies.PropertyService;
import de.supercode.superBnB.servicies.SeasonalPriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superbeb/property")
public class PropertyController {
    private PropertyService propertyService;
    private SeasonalPriceService seasonalPriceService;

    public PropertyController(PropertyService propertyService, SeasonalPriceService SeasonalPriceService) {
        this.propertyService = propertyService;
        this.seasonalPriceService = SeasonalPriceService;
    }

    // Endpoint to save a new property, restricted to admin users
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<PropertyResponseDto> saveNewProperty(@RequestBody @Validated PropertyRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveNewProperty(dto));
    }

    // Endpoint to retrieve all properties
    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
    }

    // Endpoint to retrieve a specific property by ID, restricted to admin users
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.findPropertyByIdDto(id));
    }

    // Endpoint to delete a property by ID, restricted to admin users
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Endpoint to update an existing property by ID, restricted to admin users
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(@PathVariable Long id, @RequestBody PropertyRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.updateProperty(id, dto));
    }

    // Endpoint to add seasonal pricing for a specific property, restricted to admin users
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/addSeasonalPrice/{id}")
    public ResponseEntity<SeasonalPriceResponseDto> addSeasonalPrice(@PathVariable Long id, @RequestBody SeasonalPriceRequestDto dto) {
        return ResponseEntity.ok(seasonalPriceService.addSeasonalPrice(id, dto));
    }

    // Endpoint to retrieve all seasonal prices for a specific property
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/seasonalPrices/{id}")
    public ResponseEntity<List<SeasonalPriceResponseDto>> getAllSeasonalPrices(@PathVariable Long id) {
        return ResponseEntity.ok(seasonalPriceService.getAllSeasonalPricesByProperty(id));
    }

}
