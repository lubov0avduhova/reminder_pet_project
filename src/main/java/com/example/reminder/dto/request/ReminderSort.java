package com.example.reminder.dto.request;

import java.time.LocalDateTime;

public record ReminderSort(String title, String description, LocalDateTime date, int page, int size) {
}
