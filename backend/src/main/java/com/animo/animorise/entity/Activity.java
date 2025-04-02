package com.animo.animorise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "activity_type_id", nullable = false)
    private ActivityType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String description;
    private boolean isRepeat;
    private LocalDateTime scheduleStart = LocalDateTime.now();
    private int repeatEvery;
    @Enumerated(EnumType.STRING)
    private RepeatUnit repeatUnit;

    private boolean notified;


}