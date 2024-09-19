package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.PropertySaveRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.servicies.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/superbeb/property")
// Implement CRUD operations for Property here (e.g., GET, POST, PUT, DELETE)
public class PropertyController {
    PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    private ResponseEntity<PropertyResponseDto> saveNewProperty(@RequestBody PropertySaveRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveNewProperty(dto));
    }

    @GetMapping
    private ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        try {
            return ResponseEntity.ok(propertyService.getAllProperties());
        } catch(NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
