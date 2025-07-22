package com.example.reminder.controller;

import com.example.reminder.dto.response.FullReminderResponse;
import com.example.reminder.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sort")
@RequiredArgsConstructor
public class SortReminderController {

    private final ReminderService service;

    /**
     * Сортировка (по названию, дате, времени)
     * @param pageable поля сортировки
     * @return список результата
     */
    @GetMapping
    public List<FullReminderResponse> findAllRemindersBySort(@PageableDefault(value = 2) Pageable pageable){
        return service.findAllRemindersBySort(pageable);
    }
}