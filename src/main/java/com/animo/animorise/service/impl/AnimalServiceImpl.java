package com.animo.animorise.service.impl;

import com.animo.animorise.entity.Animal;
import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.entity.HealthStatus;
import com.animo.animorise.entity.User;
import com.animo.animorise.exception.animal.AnimalNotFoundException;
import com.animo.animorise.repository.AnimalRepository;
import com.animo.animorise.repository.UserRepository;
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
                (animal.getOwner() != null) ? animal.getOwner().getId() : null // Check for null before calling getId()
        );
    }

    // Converts a DTO to entity
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
}
