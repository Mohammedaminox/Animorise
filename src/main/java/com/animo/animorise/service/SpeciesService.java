package com.animo.animorise.service;

import com.animo.animorise.dto.SpeciesDto;

import java.util.List;

public interface SpeciesService {
    SpeciesDto createSpecies(SpeciesDto speciesDto);
    SpeciesDto updateSpecies(Long id, SpeciesDto speciesDto);
    void deleteSpecies(Long id);
    List<SpeciesDto> getAllSpecies();
}