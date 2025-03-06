package com.animo.animorise.controller;

import com.animo.animorise.dto.ActivityTypeDto;
import com.animo.animorise.service.ActivityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-types")
@RequiredArgsConstructor
public class ActivityTypeController {
    private final ActivityTypeService activityTypeService;

    @PreAuthorize("hasRole('VETERIANAIRE')")
    @PostMapping
    public ResponseEntity<ActivityTypeDto> createActivityType(@RequestBody ActivityTypeDto activityTypeDto) {
        return ResponseEntity.ok(activityTypeService.createActivityType(activityTypeDto));
    }

    @PreAuthorize("hasRole('VETERIANAIRE')")
    @PutMapping("/{id}")
    public ResponseEntity<ActivityTypeDto> updateActivityType(@PathVariable Long id, @RequestBody ActivityTypeDto activityTypeDto) {
        return ResponseEntity.ok(activityTypeService.updateActivityType(id, activityTypeDto));
    }

    @PreAuthorize("hasRole('VETERIANAIRE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityType(@PathVariable Long id) {
        activityTypeService.deleteActivityType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ActivityTypeDto>> getAllActivityTypes() {
        return ResponseEntity.ok(activityTypeService.getAllActivityTypes());
    }
}