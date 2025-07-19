package com.example.reminder.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReminderErrorResponse {
    private String message;
    private LocalDateTime date;
}
