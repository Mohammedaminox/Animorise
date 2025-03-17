// src/main/java/com/animo/animorise/service/impl/ActivitySchedulerServiceImpl.java
package com.animo.animorise.service.impl;

import com.animo.animorise.dto.ActivityDto;
import com.animo.animorise.entity.Activity;
import com.animo.animorise.repository.ActivityRepository;
import com.animo.animorise.service.ActivitySchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivitySchedulerServiceImpl implements ActivitySchedulerService {
    private final ActivityRepository activityRepository;
    private final NotificationServiceImpl notificationServiceImpl;

    @Async
    @Scheduled(fixedRate = 900000) // Runs every 15 minutes
    @Override
    public void checkAndNotifyActivities() {
        LocalDateTime now = LocalDateTime.now();
        List<Activity> activities = activityRepository.findAllByScheduleStartBeforeAndNotifiedFalse(now);
        for (Activity activity : activities) {
            notificationServiceImpl.notifyUser(convertToDto(activity));
            activity.setNotified(true);
            activityRepository.save(activity);
        }
    }

    private ActivityDto convertToDto(Activity activity) {
        ActivityDto dto = new ActivityDto();
        dto.setAnimalId((activity.getAnimal() != null) ? activity.getAnimal().getId() : null);
        dto.setActivityTypeId((activity.getType() != null) ? activity.getType().getId() : null);
        dto.setActivityTypeName((activity.getType() != null) ? activity.getType().getName() : null);
        dto.setDescription(activity.getDescription());
        dto.setRepeat(activity.isRepeat());
        dto.setScheduleStart(activity.getScheduleStart());
        dto.setRepeatEvery(activity.getRepeatEvery());
        dto.setRepeatUnit(activity.getRepeatUnit());
        dto.setStatus(activity.getStatus());
        dto.setUserEmail(activity.getUser().getEmail());
        return dto;
    }
}