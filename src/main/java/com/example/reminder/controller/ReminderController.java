package com.example.reminder.controller;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.response.ReminderResponse;
import com.example.reminder.service.IReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {
   /*
   todo
    Создать новое напоминание https://domain/api/v1/reminder/create
Удалить ранее созданное напоминание https://domain/api/v1/reminder/delete
    */

    private final IReminderService service;

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
}
