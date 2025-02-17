package com.animo.animorise.controller;

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
    public ResponseEntity<List<Animal>> getAnimalsByOwner(@PathVariable Integer ownerId) {
        return ResponseEntity.ok(animalService.getAnimalsByOwner(ownerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id) {
        Optional<Animal> animal = animalService.getAnimalById(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{ownerId}")
    public ResponseEntity<Animal> addAnimal(@RequestBody Animal animal, @PathVariable Integer ownerId) {
        return ResponseEntity.ok(animalService.addAnimal(animal, ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal updatedAnimal) {
        return ResponseEntity.ok(animalService.updateAnimal(id, updatedAnimal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
