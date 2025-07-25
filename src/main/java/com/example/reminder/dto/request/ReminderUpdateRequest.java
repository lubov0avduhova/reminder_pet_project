package com.example.reminder.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReminderUpdateRequest(
        @Size(max = 255, message = "Краткое описание не должно быть больше 255 символов")
        String title,

        @Size(max = 4096, message = "Полное описание не должно быть больше 4096 символов")
        String description,

        @Future(message = "Неправильно введена дата")
        LocalDateTime remind,

        @NotNull(message = "ID пользователя не должен быть пустым")
        Long userId
) {
}
