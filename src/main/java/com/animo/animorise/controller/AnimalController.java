package com.animo.animorise.controller;

import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.entity.Animal;
import com.animo.animorise.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<AnimalDto>> getAnimalsByOwner(@PathVariable Integer ownerId) {
        return ResponseEntity.ok(animalService.getAnimalsByOwner(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable Long id) {
        Optional<AnimalDto> animal = animalService.getAnimalById(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{ownerId}")
    public ResponseEntity<AnimalDto> addAnimal(@RequestBody AnimalDto animalDto, @PathVariable Integer ownerId) {
        return ResponseEntity.ok(animalService.addAnimal(animalDto, ownerId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> updateAnimal(@PathVariable Long id, @RequestBody AnimalDto updatedAnimalDto) {
        return ResponseEntity.ok(animalService.updateAnimal(id, updatedAnimalDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
