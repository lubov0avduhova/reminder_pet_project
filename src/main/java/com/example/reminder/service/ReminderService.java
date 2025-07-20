package com.example.reminder.service;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.request.ReminderUpdateRequest;
import com.example.reminder.dto.response.ReminderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface ReminderService {
    ReminderResponse createReminder(ReminderRequest reminder);

    ReminderResponse deleteReminder(@NotNull Long userId, @NotNull Long reminderId);

    ReminderResponse updateReminder(@NotNull Long reminderId, @Valid ReminderUpdateRequest reminder);
}
