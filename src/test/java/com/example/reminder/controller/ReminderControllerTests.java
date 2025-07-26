package com.example.reminder.controller;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.request.ReminderUpdateRequest;
import com.example.reminder.dto.response.FullReminderResponse;
import com.example.reminder.dto.response.ReminderResponse;
import com.example.reminder.service.reminder.ReminderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@WebMvcTest(controllers = ReminderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReminderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean //todo надо менять из-за deprecated или можно оставить?
    private ReminderService reminderService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void ReminderController_CreateReminder_ReturnReminderResponse() throws Exception {
        ReminderRequest request = ReminderRequest.builder().title("Приготовить ужин")
                .description("За 30 минут")
                .remind(LocalDateTime.now().plusMinutes(10))
                .userId(1L).build();

        ReminderResponse responseDto = ReminderResponse.builder()
                .message("Сохранение напоминания: " + request.title() + " прошло успешно").build();
        given(reminderService.createReminder(ArgumentMatchers.any()))
                .willReturn(responseDto);

        ResultActions actions = mockMvc.perform(post("/reminder/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is(responseDto.message())));

        verify(reminderService, times(1)).createReminder(ArgumentMatchers.any());
    }

    @Test
    public void ReminderController_DeleteReminder_ReturnReminderResponse() throws Exception {
        Long userId = 1L;
        Long reminderId = 1L;


        ReminderResponse responseDto = ReminderResponse.builder()
                .message("Напоминание с Id: " + reminderId + " было удалено").build();

        given(reminderService.deleteReminder(userId, reminderId))
                .willReturn(responseDto);

        ResultActions actions = mockMvc.perform(post("/reminder/delete/{userId}/{reminderId}", userId, reminderId));

        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is(responseDto.message())));

        verify(reminderService, times(1)).deleteReminder(userId, reminderId);
    }

    @Test
    public void ReminderController_UpdateReminder_ReturnReminderResponse() throws Exception {
        Long reminderId = 1L;
        ReminderUpdateRequest request = ReminderUpdateRequest.builder()
                .title("Приготовить ужин")
                .description("За 30 минут")
                .remind(LocalDateTime.now().plusMinutes(10))
                .userId(1L).build();

        ReminderResponse responseDto = ReminderResponse.builder()
                .message("Обновление напоминания прошло успешно").build();

        given(reminderService.updateReminder(reminderId, request))
                .willReturn(responseDto);

        ResultActions actions = mockMvc.perform(put("/reminder/update/{reminderId}", reminderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is(responseDto.message())));

        verify(reminderService, times(1)).updateReminder(reminderId, request);
    }

    @Test
    public void ReminderController_FindReminder_ReturnFullReminderResponse() throws Exception {
        String title = "Приготовить ужин";
        LocalDateTime remind = LocalDateTime.now().plusMinutes(10);
        FullReminderResponse response = FullReminderResponse.builder()
                .title("Приготовить ужин")
                .description("За 30 минут")
                .remind(remind).build();

        given(reminderService.findReminder(title, null, null))
                .willReturn(response);

        ResultActions actions = mockMvc.perform(get("/reminder/findReminder")
                .param("title", title));

        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(response.title())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(response.description())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.remind", startsWith(remind.truncatedTo(ChronoUnit.SECONDS).toString())));

        verify(reminderService, times(1)).findReminder(title, null, null);
    }
}
