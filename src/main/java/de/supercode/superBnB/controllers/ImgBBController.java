package de.supercode.superBnB.controllers;

import de.supercode.superBnB.servicies.ImgBBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superbeb/imgbb")
public class ImgBBController {

    private final ImgBBService imgbbService;

    public ImgBBController(ImgBBService imgbbService) {
        this.imgbbService = imgbbService;
    }

    @PostMapping("/upload/{propertyId}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable long propertyId) {
        try {
            String response = imgbbService.uploadImage(file, propertyId);
            return ResponseEntity.ok("Image uploaded successfully: " + response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }

    @PostMapping("/upload-images/{propertyId}")
    public ResponseEntity<String> uploadMoreImages (@RequestParam("file") MultipartFile[] file, @PathVariable long propertyId) {
        try {
            List<String> response = imgbbService.uploadMoreImages(file, propertyId);
            return ResponseEntity.ok("Images uploaded successfully: " + response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }
}