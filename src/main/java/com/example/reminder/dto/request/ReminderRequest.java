package com.example.reminder.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReminderRequest {
    @Size(max = 255, message = "Краткое описание не должно быть больше 255 символов")
    @NotEmpty(message = "Краткое описание не должно быть пустым")
    private String title;

    @Size(max = 4096, message = "Полное описание не должно быть больше 4096 символов")
    private String description;

    @Future(message = "Неправильно введена дата")
    @NotNull(message = "Дата не должна быть пустой")
    private LocalDateTime remind;

    @NotNull(message = "ID пользователя не должен быть пустым")
    private Long userId;

}
