package com.example.reminder.dto.response;

import java.time.LocalDateTime;

public record FullReminderResponse(String title, String description, LocalDateTime remind) {
}
