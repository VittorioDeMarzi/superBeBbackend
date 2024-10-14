package de.supercode.superBnB.controllers;

import de.supercode.superBnB.servicies.ImgBBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/superbeb/imgbb")
public class ImgBBController {

    private final ImgBBService imgbbService;

    public ImgBBController(ImgBBService imgbbService) {
        this.imgbbService = imgbbService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String response = imgbbService.uploadImage(file);
            return ResponseEntity.ok("Image uploaded successfully: " + response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }
}