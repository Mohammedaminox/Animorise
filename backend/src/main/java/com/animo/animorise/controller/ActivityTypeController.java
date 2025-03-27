package com.animo.animorise.controller;

import com.animo.animorise.dto.ActivityTypeDto;
import com.animo.animorise.dto.SpeciesDto;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/activity-types")
@RequiredArgsConstructor
public class ActivityTypeController {
    private final ActivityTypeService activityTypeService;

    private static final String UPLOAD_DIR = "backend/uploads/";


    @PreAuthorize("hasRole('VETERINERE')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ActivityTypeDto> createActivityType(@ModelAttribute ActivityTypeDto activityTypeDto) {
        handleFileUpload(activityTypeDto);
        return ResponseEntity.ok(activityTypeService.createActivityType(activityTypeDto));
    }

    @PreAuthorize("hasRole('VETERINERE')")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
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

    @GetMapping("/{id}")
    public ResponseEntity<ActivityTypeDto> getActivityTypeById(@PathVariable Long id) {
        Optional<ActivityTypeDto> activityType = activityTypeService.getActivityTypeById(id);
        return activityType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

                // Extract the file name from the original file
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                // Save the file locally with the new name (in the uploads directory)
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.write(filePath, file.getBytes());

                // Set the icon path in the DTO with just the file name (without the path)
                activityTypeDto.setIconPath(fileName);

            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file: " + e.getMessage());
            }
        }
    }

//    private void handleFileUpload(ActivityTypeDto activityTypeDto) {
//        MultipartFile file = activityTypeDto.getIcon();
//        if (file != null && !file.isEmpty()) {
//            try {
//                // Create the directory if it doesn't exist
//                File uploadDir = new File(UPLOAD_DIR);
//                if (!uploadDir.exists()) {
//                    uploadDir.mkdirs();
//                }
//
//                // Save the file locally
//                Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
//                Files.write(filePath, file.getBytes());
//
//                // Set the icon path in the DTO
//                activityTypeDto.setIconPath(filePath.toString());
//            } catch (IOException e) {
//                throw new RuntimeException("Failed to upload file: " + e.getMessage());
//            }
//        }
//    }

}