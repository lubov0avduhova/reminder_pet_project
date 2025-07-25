package com.example.reminder.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FullReminderResponse(String title, String description, LocalDateTime remind) {
}
