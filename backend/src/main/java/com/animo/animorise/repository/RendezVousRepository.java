package com.animo.animorise.repository;

import com.animo.animorise.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    List<RendezVous> findByDateTimeBetweenAndStatusNot(LocalDateTime start, LocalDateTime end, RendezVous.Status status);
    List<RendezVous> findByAnimalId(Long animalId);
    @Query("SELECT r FROM RendezVous r JOIN FETCH r.animal WHERE r.user.id = :userId")
    List<RendezVous> findByUserId(@Param("userId") Long userId);

}