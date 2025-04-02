package com.animo.animorise.controller;

import com.animo.animorise.dto.ActivityDto;
import com.animo.animorise.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ActivityDto> addActivity(@RequestBody ActivityDto activityDto) {
        ActivityDto createdActivity = activityService.addActivity(activityDto);
        return ResponseEntity.ok(createdActivity);
    }

    @PreAuthorize("hasRole('USER') or hasRole('VETERINERE')")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ActivityDto>> getActivitiesByOwner(@PathVariable Long ownerId) {
        List<ActivityDto> activities = activityService.getActivitiesByOwner(ownerId);
        return ResponseEntity.ok(activities);
    }
}