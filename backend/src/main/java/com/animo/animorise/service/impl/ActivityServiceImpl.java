package com.animo.animorise.service.impl;

import com.animo.animorise.dto.ActivityDto;
import com.animo.animorise.entity.*;
import com.animo.animorise.exception.activity.ActivityTypeNotFoundException;
import com.animo.animorise.exception.animal.AnimalNotFoundException;
import com.animo.animorise.repository.*;
import com.animo.animorise.service.ActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final AnimalRepository animalRepository;
    private final ActivityTypeRepository activityTypeRepository;

    @Transactional
    @Override
    public ActivityDto addActivity(ActivityDto activityDto) {
        Activity activity = convertToEntity(activityDto);
        return convertToDto(activityRepository.save(activity));
    }

    @Override
    public List<ActivityDto> getActivitiesByOwner(Long ownerId) {
        return activityRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ActivityDto convertToDto(Activity activity) {
        return ActivityDto.builder()
                .animalId(activity.getAnimal() != null ? activity.getAnimal().getId() : null)
                .animalName(activity.getAnimal() != null ? activity.getAnimal().getName() : "Unknown Animal")
                .activityTypeId(activity.getType() != null ? activity.getType().getId() : null)
                .activityTypeName(activity.getType() != null ? activity.getType().getName() : "Unknown Activity Type")
                .description(activity.getDescription())
                .repeat(activity.isRepeat())
                .scheduleStart(activity.getScheduleStart())
                .repeatEvery(activity.getRepeatEvery())
                .repeatUnit(activity.getRepeatUnit())
                .notified(activity.isNotified()) // Ensure this is mapped properly
                .userEmail(activity.getOwner() != null ? activity.getOwner().getEmail() : "Unknown User")
                .build();
    }

    private Activity convertToEntity(ActivityDto dto) {
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new AnimalNotFoundException("Animal not found with ID: " + dto.getAnimalId()));

        ActivityType activityType = activityTypeRepository.findById(dto.getActivityTypeId())
                .orElseThrow(() -> new ActivityTypeNotFoundException("ActivityType not found with ID: " + dto.getActivityTypeId()));

        return Activity.builder()
                .animal(animal)
                .type(activityType)
                .description(dto.getDescription())
                .isRepeat(dto.isRepeat())
                .scheduleStart(dto.getScheduleStart())
                .repeatEvery(dto.getRepeatEvery())
                .repeatUnit(dto.getRepeatUnit())
                .owner(animal.getOwner())
                .build();
    }

}
