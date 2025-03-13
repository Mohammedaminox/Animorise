package com.animo.animorise.controller;

import com.animo.animorise.dto.ActivityDto;
import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.dto.HealthStatusUpdateRequest;
import com.animo.animorise.dto.VaccinationStatusUpdateRequest;
import com.animo.animorise.entity.HealthStatus;
import com.animo.animorise.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('USER') or hasRole('VETERINERE')")
    public ResponseEntity<List<AnimalDto>> getAnimalsByOwner(@PathVariable Integer ownerId) {
        return ResponseEntity.ok(animalService.getAnimalsByOwner(ownerId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('VETERINERE')")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable Long id) {
        Optional<AnimalDto> animal = animalService.getAnimalById(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{ownerId}")
    public ResponseEntity<AnimalDto> addAnimal(@RequestBody AnimalDto animalDto, @PathVariable Integer ownerId) {
        return ResponseEntity.ok(animalService.addAnimal(animalDto, ownerId));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> updateAnimal(@PathVariable Long id, @RequestBody AnimalDto updatedAnimalDto) {
        return ResponseEntity.ok(animalService.updateAnimal(id, updatedAnimalDto));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/activities")
    public ResponseEntity<ActivityDto> addActivity(@RequestBody ActivityDto activityDto) {
        return ResponseEntity.ok(animalService.addActivity(activityDto));
    }

    @PreAuthorize("hasRole('VETERINERE')")
    @GetMapping
    public ResponseEntity<List<AnimalDto>> getAllAnimals() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }


    @PreAuthorize("hasRole('VETERINERE')")
    @PutMapping("/{id}/health-status")
    public ResponseEntity<AnimalDto> updateHealthStatus(@PathVariable Long id, @RequestBody HealthStatusUpdateRequest request) {
        return ResponseEntity.ok(animalService.updateHealthStatus(id, request.getHealthStatus()));
    }

    @PreAuthorize("hasRole('VETERINERE')")
    @PutMapping("/{id}/vaccinated")
    public ResponseEntity<AnimalDto> updateVaccinationStatus(@PathVariable Long id, @RequestBody VaccinationStatusUpdateRequest request) {
        return ResponseEntity.ok(animalService.updateVaccinationStatus(id, request.isVaccinated()));
    }

}