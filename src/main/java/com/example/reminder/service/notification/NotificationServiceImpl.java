package com.example.reminder.service.notification;

import com.example.reminder.entity.Reminder;
import com.example.reminder.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmailNotificationService emailService;
    private final TelegramBot telegramBot;

    @Override
    public void send(Reminder reminder) {
        String title = reminder.getTitle();
        String description = reminder.getDescription();
        String message = title + "\n\n" + description;

        User user = reminder.getUser();

        if (user.getEmail() != null)
            emailService.sendEmail(user.getEmail(), "Напоминание", message);

        if (user.getTelegramId() != null)
            telegramBot.sendMessage(user.getTelegramId(), message);
    }
}
