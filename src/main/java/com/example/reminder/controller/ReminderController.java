package com.example.reminder.controller;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.response.ReminderResponse;
import com.example.reminder.service.ReminderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService service;

    /**
     * Создать новое напоминание
     *
     * @param reminder напоминание
     * @return ответ добавления
     */
    @PostMapping("/create")
    public ReminderResponse createReminder(@RequestBody @Valid ReminderRequest reminder) {
        return service.createReminder(reminder);
    }

    /**
     * Удалить ранее созданное напоминание
     * @param reminderId ID напоминания
     * @return ответ удаления
     */
    @PostMapping("/delete/{userId}/{reminderId}")
    public ReminderResponse deleteReminder(@PathVariable @NotNull Long userId, @PathVariable @NotNull Long reminderId) {
        return service.deleteReminder(userId, reminderId);
    }
}
