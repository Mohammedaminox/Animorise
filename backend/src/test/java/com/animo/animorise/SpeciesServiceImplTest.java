package com.animo.animorise;

import com.animo.animorise.dto.SpeciesDto;
import com.animo.animorise.entity.Species;
import com.animo.animorise.repository.SpeciesRepository;
import com.animo.animorise.service.impl.SpeciesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpeciesServiceImplTest {

    @InjectMocks
    private SpeciesServiceImpl speciesService;

    @Mock
    private SpeciesRepository speciesRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createSpecies_ValidData_ReturnsSpeciesDto() {
        // Arrange
        Species species = new Species();
        species.setName("Dog");
        species.setIconPath("dog.png");

        when(speciesRepository.save(any(Species.class))).thenReturn(species);

        SpeciesDto speciesDto = new SpeciesDto();
        speciesDto.setName("Dog");
        speciesDto.setIconPath("dog.png");

        // Act
        SpeciesDto result = speciesService.createSpecies(speciesDto);

        // Assert
        assertNotNull(result);
        assertEquals("Dog", result.getName());
        assertEquals("dog.png", result.getIconPath());
    }

    @Test
    void updateSpecies_ExistingId_ReturnsUpdatedSpeciesDto() {
        // Arrange
        Species species = new Species();
        species.setId(1L);
        species.setName("Cat");
        species.setIconPath("cat.png");

        when(speciesRepository.findById(1L)).thenReturn(Optional.of(species));
        when(speciesRepository.save(any(Species.class))).thenReturn(species);

        SpeciesDto speciesDto = new SpeciesDto();
        speciesDto.setName("Updated Cat");
        speciesDto.setIconPath("updated_cat.png");

        // Act
        SpeciesDto result = speciesService.updateSpecies(1L, speciesDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Cat", result.getName());
        assertEquals("updated_cat.png", result.getIconPath());
    }

    @Test
    void deleteSpecies_ExistingId_DeletesSpecies() {
        // Arrange
        when(speciesRepository.existsById(1L)).thenReturn(true);

        // Act
        speciesService.deleteSpecies(1L);

        // Assert
        verify(speciesRepository, times(1)).deleteById(1L);
    }

    @Test
    void getSpeciesById_ExistingId_ReturnsSpeciesDto() {
        // Arrange
        Species species = new Species();
        species.setId(1L);
        species.setName("Bird");
        species.setIconPath("bird.png");

        when(speciesRepository.findById(1L)).thenReturn(Optional.of(species));

        // Act
        Optional<SpeciesDto> result = speciesService.getSpeciesById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Bird", result.get().getName());
        assertEquals("bird.png", result.get().getIconPath());
    }
}