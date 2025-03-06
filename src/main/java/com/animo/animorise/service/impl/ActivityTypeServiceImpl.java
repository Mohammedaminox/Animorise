package com.animo.animorise.service.impl;

import com.animo.animorise.dto.ActivityTypeDto;
import com.animo.animorise.entity.ActivityType;
import com.animo.animorise.repository.ActivityTypeRepository;
import com.animo.animorise.service.ActivityTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityTypeServiceImpl implements ActivityTypeService {
    private final ActivityTypeRepository activityTypeRepository;

    @Override
    public ActivityTypeDto createActivityType(ActivityTypeDto activityTypeDto) {
        ActivityType activityType = new ActivityType();
        activityType.setName(activityTypeDto.getName());
        ActivityType savedActivityType = activityTypeRepository.save(activityType);
        return convertToDto(savedActivityType);
    }

    @Override
    public ActivityTypeDto updateActivityType(Long id, ActivityTypeDto activityTypeDto) {
        ActivityType activityType = activityTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ActivityType not found"));
        activityType.setName(activityTypeDto.getName());
        ActivityType updatedActivityType = activityTypeRepository.save(activityType);
        return convertToDto(updatedActivityType);
    }

    @Override
    public void deleteActivityType(Long id) {
        activityTypeRepository.deleteById(id);
    }

    @Override
    public List<ActivityTypeDto> getAllActivityTypes() {
        return activityTypeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ActivityTypeDto convertToDto(ActivityType activityType) {
        ActivityTypeDto dto = new ActivityTypeDto();
        dto.setId(activityType.getId());
        dto.setName(activityType.getName());
        return dto;
    }
}