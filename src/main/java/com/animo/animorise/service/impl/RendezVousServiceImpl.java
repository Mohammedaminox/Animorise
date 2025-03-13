package com.animo.animorise.service.impl;

import com.animo.animorise.dto.RendezVousDto;
import com.animo.animorise.entity.Animal;
import com.animo.animorise.entity.RendezVous;
import com.animo.animorise.exception.animal.AnimalNotFoundException;
import com.animo.animorise.repository.AnimalRepository;
import com.animo.animorise.repository.RendezVousRepository;
import com.animo.animorise.repository.UserRepository;
import com.animo.animorise.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RendezVousServiceImpl implements RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    @Override
    public RendezVousDto createRendezVous(Long animalId,  LocalDateTime dateTime) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + animalId));

        // Check for date conflicts excluding canceled appointments
        List<RendezVous> conflicts = rendezVousRepository.findByDateTimeBetweenAndStatusNot(
                 dateTime.minusMinutes(30), dateTime.plusMinutes(30), RendezVous.Status.CANCELED);
        if (!conflicts.isEmpty()) {
            throw new IllegalArgumentException("Date conflict with another appointment");
        }

        RendezVous rendezVous = RendezVous.builder()
                .animal(animal)
                .dateTime(dateTime)
                .status(RendezVous.Status.PENDING)
                .build();
        rendezVousRepository.save(rendezVous);
        return convertToDto(rendezVous);
    }

    @Override
    public RendezVousDto acceptRendezVous(Long rendezVousId) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous not found with ID: " + rendezVousId));
        rendezVous.setStatus(RendezVous.Status.ACCEPTED);
        rendezVousRepository.save(rendezVous);
        return convertToDto(rendezVous);
    }

    @Override
    public RendezVousDto cancelRendezVous(Long rendezVousId) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous not found with ID: " + rendezVousId));
        rendezVous.setStatus(RendezVous.Status.CANCELED);
        rendezVousRepository.save(rendezVous);
        return convertToDto(rendezVous);
    }


    @Override
    public List<RendezVousDto> getRendezVousByAnimal(Long animalId) {
        return rendezVousRepository.findByAnimalId(animalId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RendezVousDto convertToDto(RendezVous rendezVous) {
        return new RendezVousDto(rendezVous.getId(), rendezVous.getAnimal().getId(), rendezVous.getDateTime(), rendezVous.getStatus());
    }
}