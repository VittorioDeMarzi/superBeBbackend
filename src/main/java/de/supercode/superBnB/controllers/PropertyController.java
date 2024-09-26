package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.PropertyRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.servicies.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/superbeb/property")
public class PropertyController {
    PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<PropertyResponseDto> saveNewProperty(@RequestBody @Validated PropertyRequestDto dto, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveNewProperty(dto));
    }

    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.findPropertyById(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> updateProperty(@PathVariable Long id, @RequestBody PropertyResponseDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.updateProperty(id, dto));

    }



}
