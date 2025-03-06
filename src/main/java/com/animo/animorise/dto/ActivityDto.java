package com.animo.animorise.dto;

import com.animo.animorise.entity.RepeatUnit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityDto {
    private Long animalId;
    private Long activityTypeId;
    private String description;
    private boolean isRepeat;
    private LocalDateTime scheduleStart;
    private int repeatEvery;
    private RepeatUnit repeatUnit;
}