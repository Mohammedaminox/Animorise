package com.animo.animorise.service;

import com.animo.animorise.dto.ActivityDto;

public interface NotificationService {
    void notifyUser(ActivityDto activity);
//    void notifyUserOfCompletion(ActivityDto activity);
}