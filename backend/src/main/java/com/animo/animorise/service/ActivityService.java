package com.animo.animorise.service;

import com.animo.animorise.dto.ActivityDto;

import java.util.List;

public interface ActivityService {
    ActivityDto addActivity(ActivityDto activityDto);
    List<ActivityDto> getActivitiesByOwner(Long ownerId);
}