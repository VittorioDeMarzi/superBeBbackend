package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.PropertySaveRequestDto;
import de.supercode.superBnB.dtos.PropertySaveResponseDto;
import de.supercode.superBnB.servicies.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/superbeb/property")
// Implement CRUD operations for Property here (e.g., GET, POST, PUT, DELETE)
public class PropertyController {
    PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    private ResponseEntity<PropertySaveResponseDto> saveNewProperty(@RequestBody PropertySaveRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveNewProperty(dto));
    }
}
