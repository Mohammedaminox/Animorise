package com.animo.animorise.service;

import com.animo.animorise.dto.RendezVousDto;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousService {
    RendezVousDto createRendezVous(Long animalId, LocalDateTime dateTime, Long userId);
    RendezVousDto acceptRendezVous(Long rendezVousId);
    RendezVousDto cancelRendezVous(Long rendezVousId);
    List<RendezVousDto> getRendezVousByAnimal(Long animalId);

    List<RendezVousDto> getRendezVousByUser(Long userId);

    List<RendezVousDto> getAllRendezVous();
}