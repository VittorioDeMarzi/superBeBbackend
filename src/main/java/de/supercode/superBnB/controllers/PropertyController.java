package de.supercode.superBnB.controllers;

import de.supercode.superBnB.dtos.PropertyRequestDto;
import de.supercode.superBnB.dtos.PropertyResponseDto;
import de.supercode.superBnB.servicies.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    private ResponseEntity<PropertyResponseDto> saveNewProperty(@RequestBody @Validated PropertyRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveNewProperty(dto));
    }

    @GetMapping
    private ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
        } catch(NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.findPropertyById(id));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<PropertyResponseDto> updateProperty(@PathVariable Long id, @RequestBody PropertyResponseDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.updateProperty(id, dto));

    }
}
