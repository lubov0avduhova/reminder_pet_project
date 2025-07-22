package com.example.reminder.dto.response;

import java.time.LocalDateTime;

public record ReminderErrorResponse(String message, LocalDateTime date){
}
