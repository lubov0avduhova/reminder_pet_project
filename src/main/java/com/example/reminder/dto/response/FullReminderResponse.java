package com.example.reminder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class FullReminderResponse {
    private String title;
    private String description;
    private LocalDateTime remind;
}
