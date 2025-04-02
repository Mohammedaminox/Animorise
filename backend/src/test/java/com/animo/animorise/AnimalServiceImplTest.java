package com.animo.animorise;

import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.entity.Animal;
import com.animo.animorise.entity.Species;
import com.animo.animorise.entity.User;
import com.animo.animorise.exception.animal.AnimalNotFoundException;
import com.animo.animorise.exception.user.OwnerNotFoundException;
import com.animo.animorise.repository.AnimalRepository;
import com.animo.animorise.repository.SpeciesRepository;
import com.animo.animorise.repository.UserRepository;
import com.animo.animorise.service.impl.AnimalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnimalServiceImplTest {

    @InjectMocks
    private AnimalServiceImpl animalService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpeciesRepository speciesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAnimalById_ExistingId_ReturnsAnimalDto() {
        // Arrange
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Buddy");
        Species species = new Species();
        species.setId(1L);
        species.setName("Dog");
        animal.setSpecies(species);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        // Act
        Optional<AnimalDto> result = animalService.getAnimalById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Buddy", result.get().getName());
    }

    @Test
    void getAnimalById_NonExistingId_ThrowsException() {
        // Arrange
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AnimalNotFoundException.class, () -> animalService.getAnimalById(1L));
    }

    @Test
    void addAnimal_ValidData_ReturnsAnimalDto() {
        // Arrange
        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("Buddy");
        animalDto.setSpeciesId(1L);
        User owner = new User();
        owner.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        Species species = new Species();
        species.setId(1L);
        when(speciesRepository.findById(1L)).thenReturn(Optional.of(species));
        Animal animal = new Animal();
        animal.setName("Buddy");
        animal.setSpecies(species);
        animal.setOwner(owner);
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        // Act
        AnimalDto result = animalService.addAnimal(animalDto, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("Buddy", result.getName());
    }

    @Test
    void addAnimal_NonExistingOwner_ThrowsException() {
        // Arrange
        AnimalDto animalDto = new AnimalDto();
        animalDto.setName("Buddy");
        animalDto.setSpeciesId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OwnerNotFoundException.class, () -> animalService.addAnimal(animalDto, 1L));
    }

    @Test
    void updateAnimal_ExistingId_ReturnsUpdatedAnimalDto() {
        // Arrange
        Animal existingAnimal = new Animal();
        existingAnimal.setId(1L);
        existingAnimal.setName("Buddy");
        Species species = new Species();
        species.setId(1L);
        existingAnimal.setSpecies(species);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(existingAnimal));
        when(speciesRepository.findById(1L)).thenReturn(Optional.of(species));
        AnimalDto updatedAnimalDto = new AnimalDto();
        updatedAnimalDto.setName("Max");
        updatedAnimalDto.setSpeciesId(1L);
        when(animalRepository.save(any(Animal.class))).thenReturn(existingAnimal);

        // Act
        AnimalDto result = animalService.updateAnimal(1L, updatedAnimalDto);

        // Assert
        assertNotNull(result);
        assertEquals("Max", result.getName());
    }

    @Test
    void updateAnimal_NonExistingId_ThrowsException() {
        // Arrange
        AnimalDto updatedAnimalDto = new AnimalDto();
        updatedAnimalDto.setName("Max");
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AnimalNotFoundException.class, () -> animalService.updateAnimal(1L, updatedAnimalDto));
    }
}