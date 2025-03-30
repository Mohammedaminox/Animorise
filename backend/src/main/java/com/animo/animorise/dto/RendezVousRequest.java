package com.animo.animorise.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RendezVousRequest {
    private Long animalId;
    private LocalDateTime dateTime;
    private Long userId;

}
