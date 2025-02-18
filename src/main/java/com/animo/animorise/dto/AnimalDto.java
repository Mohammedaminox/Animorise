package com.animo.animorise.dto;

import com.animo.animorise.entity.HealthStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto {
    private Long id;
    private String name;
    private String species;
    private String race;
    private int age;
    private String gender;
    private boolean vaccinated;
    private HealthStatus healthStatus;
    private String photoUrl;
    private Integer ownerId; // Include only the owner's ID, not the full object
}
