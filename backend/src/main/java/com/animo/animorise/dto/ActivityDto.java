package com.animo.animorise.dto;

import com.animo.animorise.entity.RepeatUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDto {
    private Long animalId;
    private String animalName;
    private Long activityTypeId;
    private String activityTypeName;
    private String description;
    private boolean repeat;
    private LocalDateTime scheduleStart = LocalDateTime.now();
    private int repeatEvery;
    private RepeatUnit repeatUnit;
    private boolean notified;
    private String userEmail;
}