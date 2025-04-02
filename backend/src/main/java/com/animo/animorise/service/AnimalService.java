package com.animo.animorise.service;

import com.animo.animorise.dto.ActivityDto;
import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.entity.Animal;
import com.animo.animorise.entity.HealthStatus;

import java.util.List;
import java.util.Optional;

public interface AnimalService {
    List<AnimalDto> getAnimalsByOwner(Long ownerId);
    Optional<AnimalDto> getAnimalById(Long id);
    AnimalDto addAnimal(AnimalDto animalDto, Long ownerId);
    AnimalDto updateAnimal(Long id, AnimalDto updatedAnimalDto);
    void deleteAnimal(Long id);

    List<AnimalDto> getAllAnimals();
    AnimalDto updateHealthStatus(Long id, HealthStatus healthStatus);
    AnimalDto updateVaccinationStatus(Long id, boolean vaccinated);

//    ActivityDto addActivity(ActivityDto activityDto);

}
