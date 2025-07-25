package com.example.reminder.service.notification;

import com.example.reminder.entity.Reminder;

public interface NotificationService {
    void send(Reminder reminder);
}
