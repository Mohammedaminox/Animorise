package com.animo.animorise.service;

import com.animo.animorise.dto.ActivityTypeDto;

import java.util.List;
import java.util.Optional;

public interface ActivityTypeService {
    ActivityTypeDto createActivityType(ActivityTypeDto activityTypeDto);
    ActivityTypeDto updateActivityType(Long id, ActivityTypeDto activityTypeDto);
    void deleteActivityType(Long id);
    List<ActivityTypeDto> getAllActivityTypes();
    Optional<ActivityTypeDto> getActivityTypeById(Long id);

}