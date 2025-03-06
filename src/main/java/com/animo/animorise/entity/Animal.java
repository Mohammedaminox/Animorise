package com.animo.animorise.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "animals")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String species; // Example: Dog, Cat, etc.

    @Column(nullable = false)
    private String race;


    @Column(nullable = false)
    private Gender gender; // Example: Male, Female, Unknown

    @Column(nullable = true)
    private boolean vaccinated;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private HealthStatus healthStatus;

    @Column(nullable = true)
    private String photoUrl; // Store URL to animal's photo

    @Column(nullable = false)
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    private User owner;

    @Column(nullable = true)
    private String medicalHistory; // Can store a JSON string or formatted history

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
