package com.animo.animorise.service.impl;

import com.animo.animorise.dto.ActivityDto;
import com.animo.animorise.service.EmailService;
import com.animo.animorise.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final EmailService emailService;

    @Override
    public void notifyUser(ActivityDto activity) {
        String subject = "Reminder: Upcoming Activity" + activity.getActivityTypeName();
        String text = "You have an upcoming activity: " + activity.getDescription();
        emailService.sendSimpleMessage(activity.getUserEmail(), subject, text); // Replace with actual user email
    }


}