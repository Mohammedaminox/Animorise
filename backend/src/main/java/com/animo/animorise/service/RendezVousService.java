package com.animo.animorise.service;

import com.animo.animorise.dto.RendezVousDto;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousService {
    RendezVousDto createRendezVous(Long animalId, LocalDateTime dateTime);
    RendezVousDto acceptRendezVous(Long rendezVousId);
    RendezVousDto cancelRendezVous(Long rendezVousId);
//    List<RendezVousDto> getRendezVousByVeterinere(Integer veterinereId, LocalDateTime start, LocalDateTime end);
    List<RendezVousDto> getRendezVousByAnimal(Long animalId);
}