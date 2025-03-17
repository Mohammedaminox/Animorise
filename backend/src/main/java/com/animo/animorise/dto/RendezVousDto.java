package com.animo.animorise.dto;

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
    private LocalDateTime dateTime;
    private Status status;
}