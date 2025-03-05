package com.animo.animorise;

import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.entity.Animal;
import com.animo.animorise.entity.Gender;
import com.animo.animorise.entity.HealthStatus;
import com.animo.animorise.exception.animal.AnimalNotFoundException;
import com.animo.animorise.repository.AnimalRepository;
import com.animo.animorise.repository.UserRepository;
import com.animo.animorise.service.impl.AnimalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AnimalServiceImpl animalService;

    private Animal animal;
    private AnimalDto animalDto;

    @BeforeEach
    void setUp() {
        animal = new Animal();
        animal.setId(1L);
        animal.setName("Tom");
        animal.setSpecies("Cat");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse("11-11-2023", formatter);

        animalDto = new AnimalDto(1L, "Tom", "Cat", "Siamese", Gender.Male, true, HealthStatus.Healthy, "url",date, 1);
    }

    @Test
    void testUpdateAnimal_Success() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        AnimalDto result = animalService.updateAnimal(1L, animalDto);

        assertNotNull(result);
        assertEquals("Tom", result.getName());
        verify(animalRepository, times(1)).save(any(Animal.class));
    }

    @Test
    void testUpdateAnimal_AnimalNotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnimalNotFoundException.class, () -> {
            animalService.updateAnimal(1L, animalDto);
        });

        verify(animalRepository, never()).save(any(Animal.class));
    }

    @Test
    void testAddAnimal_UserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            animalService.addAnimal(animalDto, 1);
        });

        verify(animalRepository, never()).save(any(Animal.class));
    }
}
