package com.example.reminder.controller;

import com.example.reminder.dto.response.FullReminderResponse;
import com.example.reminder.service.reminder.ReminderService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = FilterReminderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FilterReminderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean //todo надо менять из-за deprecated или можно оставить?
    private ReminderService reminderService;

    @Test
    public void FilterReminderController_FindAllRemindersByFilter_ReturnPageOfFullReminderResponse() throws Exception {
        // arrange
        LocalDateTime remindAfter = LocalDateTime.now().plusMinutes(10);
        LocalDateTime remindBefore = remindAfter.minusDays(2);

        Pageable pageable = PageRequest.of(0, 10);

        FullReminderResponse firstResponse = FullReminderResponse.builder()
                .title("Приготовить ужин")
                .description("За 30 минут").remind(remindAfter).build();

        FullReminderResponse secondResponse = FullReminderResponse.builder()
                .title("Помыть машину")
                .description("Найти свободное время")
                .remind(remindBefore.plusDays(1)).build();

        List<FullReminderResponse> responses = List.of(firstResponse, secondResponse);
        Page<FullReminderResponse> responsesPage = new PageImpl<>(responses, pageable, responses.size());


        given(reminderService.findAllRemindersByFilter(remindAfter, remindBefore, pageable)).willReturn(responsesPage);

        // act
        ResultActions actions = mockMvc.perform(get("/filter").param("remindAfter", String.valueOf(remindAfter))
                .param("remindBefore", String.valueOf(remindBefore))
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize())));


        // assert
        actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title", CoreMatchers.is("Приготовить ужин")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description", CoreMatchers.is("За 30 минут")));
        verify(reminderService, times(1)).findAllRemindersByFilter(remindAfter, remindBefore, pageable);

    }
}