package com.animo.animorise.controller;

import com.animo.animorise.dto.ActivityTypeDto;
import com.animo.animorise.dto.SpeciesDto;
import com.animo.animorise.service.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {
    private final SpeciesService speciesService;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    @PreAuthorize("hasRole('VETERINERE')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<SpeciesDto> createSpecies(@ModelAttribute SpeciesDto speciesDto) {
        handleFileUpload(speciesDto);
        return ResponseEntity.ok(speciesService.createSpecies(speciesDto));
    }


    @PreAuthorize("hasRole('VETERINERE')")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<SpeciesDto> updateSpecies(@PathVariable Long id, @ModelAttribute SpeciesDto speciesDto) {
        handleFileUpload(speciesDto);
        return ResponseEntity.ok(speciesService.updateSpecies(id, speciesDto));
    }


    @PreAuthorize("hasRole('VETERINERE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable Long id) {
        speciesService.deleteSpecies(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SpeciesDto>> getAllSpecies() {
        return ResponseEntity.ok(speciesService.getAllSpecies());
    }

    private void handleFileUpload(SpeciesDto speciesDto) {
        MultipartFile file = speciesDto.getIcon();
        if (file != null && !file.isEmpty()) {
            try {
                // Create the directory if it doesn't exist
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Save the file locally
                Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
                Files.write(filePath, file.getBytes());

                // Set the icon path in the DTO
                speciesDto.setIconPath(filePath.toString());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file: " + e.getMessage());
            }
        }
    }
}