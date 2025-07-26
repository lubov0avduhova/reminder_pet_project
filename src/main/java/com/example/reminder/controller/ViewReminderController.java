package com.example.reminder.controller;

import com.example.reminder.dto.response.FullReminderResponse;
import com.example.reminder.service.reminder.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/list")
@RequiredArgsConstructor
public class ViewReminderController {

    private final ReminderService reminderService;

    /**
     * Получение списка с пагинацией и сортировкой
     * @param pageable параметры пагинации и сортировки
     * @return объект с total и current
     */
    @GetMapping()
    public Map<String, Object> getPaginatedList(@PageableDefault(size = 10) Pageable pageable) {
        Page<FullReminderResponse> page = reminderService.findAllRemindersBySort(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("total", page.getTotalElements());
        response.put("current", page.getContent());

        return response;
    }
}
