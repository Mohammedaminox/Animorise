package com.animo.animorise.service;

import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.dto.SpeciesDto;

import java.util.List;
import java.util.Optional;

public interface SpeciesService {
    SpeciesDto createSpecies(SpeciesDto speciesDto);
    SpeciesDto updateSpecies(Long id, SpeciesDto speciesDto);
    void deleteSpecies(Long id);
    Optional<SpeciesDto> getSpeciesById(Long id);
    List<SpeciesDto> getAllSpecies();
}