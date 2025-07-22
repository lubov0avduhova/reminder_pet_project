package com.example.reminder.controller;

import com.example.reminder.dto.response.FullReminderResponse;
import com.example.reminder.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterReminderController {
    private final ReminderService service;

    /**
     * Фильтр (по дате, времени)
     *
     * @param remindAfter  дата, с которой искать
     * @param remindBefore дата, до которой искать
     * @param pageable     пагинация и фильтрация
     * @return список напоминаний
     */
    @GetMapping
    //todo или будет одна дата?
    public Page<FullReminderResponse> findAllRemindersByFilter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime remindAfter,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime remindBefore,
                                                               @PageableDefault(value = 2) Pageable pageable) {
        return service.findAllRemindersByFilter(remindAfter, remindBefore, pageable);
    }
}
