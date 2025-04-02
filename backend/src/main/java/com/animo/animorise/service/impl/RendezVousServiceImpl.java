package com.animo.animorise.service.impl;

import com.animo.animorise.dto.RendezVousDto;
import com.animo.animorise.entity.Animal;
import com.animo.animorise.entity.RendezVous;
import com.animo.animorise.entity.User;
import com.animo.animorise.exception.animal.AnimalNotFoundException;
import com.animo.animorise.exception.animal.UserNotFoundException;
import com.animo.animorise.exception.rendezvous.RendezVousNotFoundException;
import com.animo.animorise.repository.AnimalRepository;
import com.animo.animorise.repository.RendezVousRepository;
import com.animo.animorise.repository.UserRepository;
import com.animo.animorise.service.RendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public RendezVousDto createRendezVous(Long animalId, LocalDateTime dateTime, Long userId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + animalId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // Check for date conflicts
        List<RendezVous> conflicts = rendezVousRepository.findByDateTimeBetweenAndStatusNot(
                dateTime.minusMinutes(30), dateTime.plusMinutes(30), RendezVous.Status.CANCELED);
        if (!conflicts.isEmpty()) {
            throw new IllegalArgumentException("Date conflict with another appointment");
        }

        RendezVous rendezVous = RendezVous.builder()
                .animal(animal)
                .user(user)
                .dateTime(dateTime)
                .status(RendezVous.Status.PENDING)
                .build();
        rendezVousRepository.save(rendezVous);
        return convertToDto(rendezVous);
    }

    @Override
    @Transactional
    public RendezVousDto acceptRendezVous(Long rendezVousId) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RendezVousNotFoundException("Rendez-vous not found with ID: " + rendezVousId));
        rendezVous.setStatus(RendezVous.Status.ACCEPTED);
        rendezVousRepository.save(rendezVous);
        return convertToDto(rendezVous);
    }

    @Override
    @Transactional
    public RendezVousDto cancelRendezVous(Long rendezVousId) {
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new RendezVousNotFoundException("Rendez-vous not found with ID: " + rendezVousId));
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
    @Override
    @Transactional
    public List<RendezVousDto> getRendezVousByUser(Long userId) {
        return rendezVousRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RendezVousDto> getAllRendezVous() {
        return rendezVousRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RendezVousDto convertToDto(RendezVous rendezVous) {
        return new RendezVousDto(
                rendezVous.getId(),
                rendezVous.getAnimal().getId(),
                rendezVous.getAnimal().getName(),
                rendezVous.getAnimal().getRace(),
                rendezVous.getAnimal().getGender(),
                rendezVous.getAnimal().isVaccinated(),
                rendezVous.getAnimal().getHealthStatus(),
                rendezVous.getDateTime(),
                rendezVous.getStatus(),
                rendezVous.getUser().getId()
        );
    }
}