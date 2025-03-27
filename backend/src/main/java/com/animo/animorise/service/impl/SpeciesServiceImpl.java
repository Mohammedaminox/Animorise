package com.animo.animorise.service.impl;

import com.animo.animorise.dto.AnimalDto;
import com.animo.animorise.dto.SpeciesDto;
import com.animo.animorise.entity.Species;
import com.animo.animorise.repository.SpeciesRepository;
import com.animo.animorise.service.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeciesServiceImpl implements SpeciesService {
    private final SpeciesRepository speciesRepository;
    private final Path rootLocation = Paths.get("upload-dir"); // Directory to store uploaded files

    @Override
    public SpeciesDto createSpecies(SpeciesDto speciesDto) {
        Species species = new Species();
        species.setName(speciesDto.getName());
        species.setIconPath(speciesDto.getIconPath());
        Species savedSpecies = speciesRepository.save(species);
        return convertToDto(savedSpecies);
    }

    @Override
    public SpeciesDto updateSpecies(Long id, SpeciesDto speciesDto) {
        Species species = speciesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Species not found with id: " + id));
        species.setName(speciesDto.getName());
        if (speciesDto.getIconPath() != null) {
            species.setIconPath(speciesDto.getIconPath());
        }
        Species updatedSpecies = speciesRepository.save(species);
        return convertToDto(updatedSpecies);
    }

    @Override
    public void deleteSpecies(Long id) {
        if (!speciesRepository.existsById(id)) {
            throw new RuntimeException("Species not found with id: " + id);
        }
        speciesRepository.deleteById(id);
    }

    @Override
    public List<SpeciesDto> getAllSpecies() {
        return speciesRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SpeciesDto> getSpeciesById(Long id) {
        return speciesRepository.findById(id)
                .map(this::convertToDto);
    }

    private SpeciesDto convertToDto(Species species) {
        SpeciesDto dto = new SpeciesDto();
        dto.setId(species.getId());
        dto.setName(species.getName());
        dto.setIconPath(species.getIconPath());
        return dto;
    }

//    private String storeFile(MultipartFile file) {
//        try {
//            if (file.isEmpty()) {
//                throw new RuntimeException("Failed to store empty file.");
//            }
//            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
//            return file.getOriginalFilename();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store file.", e);
//        }
//    }
}