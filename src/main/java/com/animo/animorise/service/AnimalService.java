package com.animo.animorise.service;

import com.animo.animorise.entity.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalService {
    List<Animal> getAnimalsByOwner(Integer ownerId);
    Optional<Animal> getAnimalById(Long id);
    Animal addAnimal(Animal animal, Integer ownerId);
    Animal updateAnimal(Long id, Animal updatedAnimal);
    void deleteAnimal(Long id);
}
