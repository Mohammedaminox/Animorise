package com.animo.animorise.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String description;
    private boolean isRepeat;
    private LocalDateTime scheduleStart = LocalDateTime.now();
    private int repeatEvery;
    @Enumerated(EnumType.STRING)
    private RepeatUnit repeatUnit;

    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean notified;

    public enum Status {
        PENDING,
        COMPLETED,
        CANCELED
    }
}