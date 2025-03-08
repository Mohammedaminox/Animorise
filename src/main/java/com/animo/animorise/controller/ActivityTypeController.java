package com.animo.animorise.controller;

import com.animo.animorise.dto.ActivityTypeDto;
import com.animo.animorise.service.ActivityTypeService;
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
@RequestMapping("/api/activity-types")
@RequiredArgsConstructor
public class ActivityTypeController {
    private final ActivityTypeService activityTypeService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";


    @PreAuthorize("hasRole('VETERINERE')")
    @PostMapping
    public ResponseEntity<ActivityTypeDto> createActivityType(@ModelAttribute ActivityTypeDto activityTypeDto) {
        handleFileUpload(activityTypeDto);
        return ResponseEntity.ok(activityTypeService.createActivityType(activityTypeDto));
    }

    @PreAuthorize("hasRole('VETERINERE')")
    @PutMapping("/{id}")
    public ResponseEntity<ActivityTypeDto> updateActivityType(@PathVariable Long id, @ModelAttribute ActivityTypeDto activityTypeDto) {
        handleFileUpload(activityTypeDto);
        return ResponseEntity.ok(activityTypeService.updateActivityType(id, activityTypeDto));
    }

    @PreAuthorize("hasRole('VETERINERE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityType(@PathVariable Long id) {
        activityTypeService.deleteActivityType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ActivityTypeDto>> getAllActivityTypes() {
        return ResponseEntity.ok(activityTypeService.getAllActivityTypes());
    }

    private void handleFileUpload(ActivityTypeDto activityTypeDto) {
        MultipartFile file = activityTypeDto.getIcon();
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
                activityTypeDto.setIconPath(filePath.toString());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file: " + e.getMessage());
            }
        }
    }

}