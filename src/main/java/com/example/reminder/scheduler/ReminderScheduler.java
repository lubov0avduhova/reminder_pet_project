package com.example.reminder.scheduler;

import com.example.reminder.service.reminder.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final ReminderService reminderService;

    @Scheduled(fixedRate = 60000)
    public void checkReminders() {
        reminderService.checkDueReminders();
    }
}
