package com.animo.animorise.repository;

import com.animo.animorise.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    List<RendezVous> findByDateTimeBetweenAndStatusNot(LocalDateTime start, LocalDateTime end, RendezVous.Status status);
    List<RendezVous> findByAnimalId(Long animalId);
}