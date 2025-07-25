package com.example.reminder.controller;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.request.ReminderUpdateRequest;
import com.example.reminder.dto.response.FullReminderResponse;
import com.example.reminder.dto.response.ReminderResponse;
import com.example.reminder.service.reminder.ReminderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
     *
     * @param reminderId ID напоминания
     * @return ответ удаления
     */
    @PostMapping("/delete/{userId}/{reminderId}")
    public ReminderResponse deleteReminder(@PathVariable @NotNull Long userId, @PathVariable @NotNull Long reminderId) {
        return service.deleteReminder(userId, reminderId);
    }


    /**
     * Обновить напоминание
     * @param reminderId ID напоминания
     * @param reminder   само напоминание
     * @return ответ обновления
     */
    @PutMapping("/update/{reminderId}")
    public ReminderResponse updateReminder(@PathVariable @NotNull Long reminderId, @RequestBody @Valid ReminderUpdateRequest reminder) {
        return service.updateReminder(reminderId, reminder);
    }


    /**
     * Создать новое напоминание
     * @param title краткое описание
     * @param description полное описание
     * @param date дата
     * @return ответ создания
     */
    @GetMapping("/findReminder")
    public FullReminderResponse findReminder(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        return service.findReminder(title, description, date);
    }
}
