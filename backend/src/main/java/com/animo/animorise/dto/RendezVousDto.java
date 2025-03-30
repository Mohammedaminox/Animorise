package com.animo.animorise.dto;

import com.animo.animorise.entity.Gender;
import com.animo.animorise.entity.HealthStatus;
import com.animo.animorise.entity.RendezVous.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RendezVousDto {
    private Long id;
    private Long animalId;
    private String animalName;
    private String animalRace;
    private Gender animalGender;
    private boolean animalVaccinated;
    private HealthStatus animalHealthStatus;
    private LocalDateTime dateTime;
    private Status status;
    private Long userId;

}