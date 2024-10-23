package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.PropertyRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.dtos.SeasonalPriceRequestDto;
import de.supercode.superBnB.dtos.SeasonalPriceResponseDto;
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

    // Save a new property, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<PropertyResponseDto> saveNewProperty(@RequestBody @Validated PropertyRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveNewProperty(dto));
    }

    // Retrieve all properties, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
    }

    // Retrieve all public properties
    @GetMapping("/public")
    public ResponseEntity<List<PropertyResponseDto>> getAllPublicProperties() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllPublicProperties());
    }

    // Retrieve a specific property by ID
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.findPropertyDtoById(id));
    }


    // Delete a property by ID, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable Long id) {
        propertyService.deleteById(id);
        return ResponseEntity.ok("Property was deleted successfully");
    }

    // Update an existing property by ID, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(@PathVariable Long id, @RequestBody PropertyRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.updateProperty(id, dto));
    }

    // Add seasonal pricing for a specific property, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/addSeasonalPrice/{id}")
    public ResponseEntity<SeasonalPriceResponseDto> addSeasonalPrice(@PathVariable Long id, @RequestBody SeasonalPriceRequestDto dto) {
        return ResponseEntity.ok(seasonalPriceService.addSeasonalPrice(id, dto));
    }

    // Retrieve all seasonal prices for a specific property, ADMIN
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/seasonalPrices/{id}")
    public ResponseEntity<List<SeasonalPriceResponseDto>> getAllSeasonalPrices(@PathVariable Long id) {
        return ResponseEntity.ok(seasonalPriceService.getAllSeasonalPricesByProperty(id));
    }

    // Change the visibility of a property, ADMIN
    @PutMapping("/change-visibility/{propertyId}")
    public ResponseEntity<PropertyResponseDto> changeVisibility(@PathVariable long propertyId) {

        return ResponseEntity.ok(propertyService.changeVisibility(propertyId));
    }
}
