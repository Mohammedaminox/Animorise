package com.animo.animorise.service.impl;

import com.animo.animorise.dto.ActivityDto;
import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.entity.*;
import com.animo.animorise.exception.activity.ActivityTypeNotFoundException;
import com.animo.animorise.exception.animal.AnimalNotFoundException;
import com.animo.animorise.repository.*;
import com.animo.animorise.service.AnimalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final ActivityTypeRepository activityTypeRepository;

    @Override
    public List<AnimalDto> getAnimalsByOwner(Integer ownerId) {
        return animalRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AnimalDto> getAnimalById(Long id) {
        return animalRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public AnimalDto addAnimal(AnimalDto animalDto, Integer ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found!"));

        Animal animal = convertToEntity(animalDto);
        animal.setOwner(owner);
        Animal savedAnimal = animalRepository.save(animal);

        return convertToDto(savedAnimal);
    }

    @Transactional
    @Override
    public AnimalDto updateAnimal(Long id, AnimalDto updatedAnimalDto) {
        return animalRepository.findById(id).map(animal -> {
            animal.setName(updatedAnimalDto.getName());
            animal.setSpecies(updatedAnimalDto.getSpecies());
            animal.setRace(updatedAnimalDto.getRace());
            animal.setGender(updatedAnimalDto.getGender());
            animal.setVaccinated(updatedAnimalDto.isVaccinated());
            animal.setHealthStatus(updatedAnimalDto.getHealthStatus());
            animal.setPhotoUrl(updatedAnimalDto.getPhotoUrl());
            animal.setBirthDate(updatedAnimalDto.getBirthDate());

            Animal updatedAnimal = animalRepository.save(animal);
            return convertToDto(updatedAnimal);
        }).orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + id));
    }

    private AnimalDto convertToDto(Animal animal) {
        return new AnimalDto(
                animal.getId(),
                animal.getName(),
                animal.getSpecies(),
                animal.getRace(),
                animal.getGender(),
                animal.isVaccinated(),
                animal.getHealthStatus(),
                animal.getPhotoUrl(),
                animal.getBirthDate(),
                (animal.getOwner() != null) ? animal.getOwner().getId() : null
        );
    }

    private Animal convertToEntity(AnimalDto dto) {
        Animal animal = new Animal();
        animal.setName(dto.getName());
        animal.setSpecies(dto.getSpecies());
        animal.setRace(dto.getRace());
        animal.setGender(dto.getGender());
        animal.setVaccinated(dto.isVaccinated());
        animal.setHealthStatus(dto.getHealthStatus());
        animal.setPhotoUrl(dto.getPhotoUrl());
        animal.setBirthDate(dto.getBirthDate());
        return animal;
    }

    @Override
    public void deleteAnimal(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new AnimalNotFoundException("Animal not found with ID: " + id);
        }
        animalRepository.deleteById(id);
    }

    @Override
    public List<AnimalDto> getAllAnimals() {
        return animalRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AnimalDto updateHealthStatus(Long id, HealthStatus healthStatus) {
        return animalRepository.findById(id).map(animal -> {
            animal.setHealthStatus(healthStatus);
            Animal updatedAnimal = animalRepository.save(animal);
            return convertToDto(updatedAnimal);
        }).orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + id));
    }

    @Transactional
    @Override
    public ActivityDto addActivity(ActivityDto activityDto) {
        Animal animal = animalRepository.findById(activityDto.getAnimalId())
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + activityDto.getAnimalId()));

        ActivityType activityType = activityTypeRepository.findById(activityDto.getActivityTypeId())
                .orElseThrow(() -> new ActivityTypeNotFoundException("ActivityType not found with id: " +  activityDto.getActivityTypeId()));
        Activity activity = new Activity();
        activity.setAnimal(animal);
        activity.setType(activityType);
        activity.setDescription(activityDto.getDescription());
        activity.setRepeat(activityDto.isRepeat());
        activity.setScheduleStart(activityDto.getScheduleStart());
        activity.setRepeatEvery(activityDto.getRepeatEvery());
        activity.setRepeatUnit(activityDto.getRepeatUnit());

        Activity savedActivity = activityRepository.save(activity);
        return convertToDto(savedActivity);
    }

    private ActivityDto convertToDto(Activity activity) {
        ActivityDto dto = new ActivityDto();
        dto.setAnimalId((activity.getAnimal() != null) ? activity.getAnimal().getId() : null);
        dto.setActivityTypeId((activity.getType() != null) ? activity.getType().getId() : null);
        dto.setDescription(activity.getDescription());
        dto.setRepeat(activity.isRepeat());
        dto.setScheduleStart(activity.getScheduleStart());
        dto.setRepeatEvery(activity.getRepeatEvery());
        dto.setRepeatUnit(activity.getRepeatUnit());
        return dto;
    }
}
