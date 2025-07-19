package com.example.reminder.service;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.response.ReminderResponse;

public interface IReminderService {
    ReminderResponse createReminder(ReminderRequest reminder);
}
