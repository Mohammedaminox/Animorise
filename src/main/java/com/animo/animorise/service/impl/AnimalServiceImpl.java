package com.animo.animorise.service.impl;

import com.animo.animorise.entity.Animal;
import com.animo.animorise.entity.User;
import com.animo.animorise.repository.AnimalRepository;
import com.animo.animorise.repository.UserRepository;
import com.animo.animorise.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Override
    public List<Animal> getAnimalsByOwner(Integer ownerId) {
        return animalRepository.findByOwnerId(ownerId);
    }

    @Override
    public Optional<Animal> getAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    @Override
    public Animal addAnimal(Animal animal, Integer ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found!"));
        animal.setOwner(owner);
        return animalRepository.save(animal);
    }

    @Override
    public Animal updateAnimal(Long id, Animal updatedAnimal) {
        return animalRepository.findById(id).map(animal -> {
            animal.setName(updatedAnimal.getName());
            animal.setSpecies(updatedAnimal.getSpecies());
            animal.setRace(updatedAnimal.getRace());
            animal.setAge(updatedAnimal.getAge());
            animal.setGender(updatedAnimal.getGender());
            animal.setVaccinated(updatedAnimal.isVaccinated());
            animal.setHealthStatus(updatedAnimal.getHealthStatus());
            animal.setPhotoUrl(updatedAnimal.getPhotoUrl());
            animal.setMedicalHistory(updatedAnimal.getMedicalHistory());
            return animalRepository.save(animal);
        }).orElseThrow(() -> new RuntimeException("Animal not found!"));
    }

    @Override
    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }
}
