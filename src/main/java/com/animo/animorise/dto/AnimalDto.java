package com.animo.animorise.dto;

import com.animo.animorise.entity.Gender;
import com.animo.animorise.entity.HealthStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto {
    private Long id;
    private String name;
    private String species;
    private String race;
    private Gender gender;
    private boolean vaccinated;
    private HealthStatus healthStatus;
    private String photoUrl;
    private LocalDate birthDate;
    private Integer ownerId; // Include only the owner's ID, not the full object
}
