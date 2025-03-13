package com.animo.animorise.dto;

import com.animo.animorise.entity.Activity;
import com.animo.animorise.entity.RepeatUnit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityDto {
    private Long animalId;
    private Long activityTypeId;
    private String activityTypeName;
    private String description;
    private boolean isRepeat;
    private LocalDateTime scheduleStart = LocalDateTime.now();
    private int repeatEvery;
    private RepeatUnit repeatUnit;
    private boolean notified;
    private Activity.Status status;
    private String userEmail;
}