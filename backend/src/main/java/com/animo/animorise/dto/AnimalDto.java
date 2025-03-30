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
    private Long speciesId;
    private String speciesName;
    private String speciesIcon;
    private String race;
    private Gender gender;
    private boolean vaccinated;
    private HealthStatus healthStatus;
    private LocalDate birthDate;
    private Long ownerId;


}
