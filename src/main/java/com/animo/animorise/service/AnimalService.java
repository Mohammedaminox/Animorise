package com.animo.animorise.service;

import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.entity.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalService {
    List<AnimalDto> getAnimalsByOwner(Integer ownerId);
    Optional<AnimalDto> getAnimalById(Long id);
    AnimalDto addAnimal(AnimalDto animalDto, Integer ownerId);
    AnimalDto updateAnimal(Long id, AnimalDto updatedAnimalDto);
    void deleteAnimal(Long id);
}
