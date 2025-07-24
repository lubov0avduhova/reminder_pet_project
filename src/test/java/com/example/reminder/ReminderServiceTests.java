package com.example.reminder;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.request.ReminderUpdateRequest;
import com.example.reminder.dto.response.ReminderResponse;
import com.example.reminder.entity.Reminder;
import com.example.reminder.entity.User;
import com.example.reminder.exception.ReminderException;
import com.example.reminder.exception.UserNotFoundException;
import com.example.reminder.mapper.ReminderMapper;
import com.example.reminder.repository.ReminderRepository;
import com.example.reminder.repository.UserRepository;
import com.example.reminder.service.ReminderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceTests {

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReminderMapper mapper;

    @InjectMocks
    private ReminderServiceImpl service;


    @Test
    public void ReminderService_CreateReminder_ReturnUserNotFound() {
        //arrange
        Long id = -1L;

        ReminderRequest request = ReminderRequest.builder().userId(id).build();

        //act
        UserNotFoundException ex = assertThrows(
                UserNotFoundException.class,
                () -> service.createReminder(request)
        );

        //assert
        assertEquals("Пользователь не найден", ex.getMessage());
    }


    @Test
    public void ReminderService_CreateReminder_ReturnReminderResponse() {
        //arrange
        ReminderRequest request = ReminderRequest.builder()
                .title("Проверить почту")
                .description("Проверить и отсортировать по папкам")
                .remind(LocalDateTime.now().plusMinutes(10))
                .userId(1L)
                .build();

        Reminder reminder = Reminder.builder().
                id(1L)
                .title("Проверить почту")
                .description("Проверить и отсортировать по папкам")
                .remind(LocalDateTime.now().plusMinutes(10))
                .user(new User(1L, "Maksim", "12345", "maksim@gmail.com", new ArrayList<>()))
                .build();

        when(mapper.toEntity(request)).thenReturn(reminder);

        when(userRepository.existsById(request.userId())
        ).thenReturn(true);

        when(reminderRepository.save(Mockito.any(Reminder.class))).thenReturn(reminder);

        //act
        ReminderResponse response = service.createReminder(request);

        //assert

        assertEquals("Сохранение напоминания: " + reminder.getTitle() + " прошло успешно",
                response.message());
    }

    @Test
    public void ReminderService_CreateReminder_ReturnReminderException() {
        //arrange
        ReminderRequest request = ReminderRequest.builder()
                .title("Проверить почту")
                .description("Проверить и отсортировать по папкам")
                .remind(LocalDateTime.now().plusMinutes(10))
                .userId(1L)
                .build();

        Reminder reminder = Reminder.builder().
                id(null)
                .build();

        when(mapper.toEntity(request)).thenReturn(reminder);

        when(userRepository.existsById(request.userId())
        ).thenReturn(true);

        when(reminderRepository.save(Mockito.any(Reminder.class))).thenReturn(reminder);

        //act
        ReminderException ex = assertThrows(
                ReminderException.class,
                () -> service.createReminder(request));

        //assert
        assertEquals("Не удалось сохранить объект", ex.getMessage());
    }


    @Test
    public void ReminderService_DeleteReminder_ReturnUserNotFoundException() {
        //arrange
        Long id = -1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        //act
        UserNotFoundException ex = assertThrows(
                UserNotFoundException.class,
                () -> service.deleteReminder(id, id));

        //assert
        assertEquals("Пользователь не найден", ex.getMessage());
    }


    @Test
    public void ReminderService_DeleteReminder_ReturnReminderException() {
        //arrange
        User user = User.builder()
                .id(1L)
                .username("Maksim")
                .email("maksim@gmail.com")
                .telegramId("12345")
                .reminders(new ArrayList<>())
                .build();

        Reminder reminder = Reminder.builder()
                .id(1L)
                .title("Проверить почту")
                .description("Проверить и отсортировать по папкам")
                .remind(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();

        user.getReminders().add(reminder);

        Long notExistsId = -2L;

        when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        //act
        ReminderException ex = assertThrows(
                ReminderException.class,
                () -> service.deleteReminder(user.getId(), notExistsId));

        //assert
        assertEquals("Напоминание не найдено", ex.getMessage());
    }

    @Test
    public void ReminderService_DeleteReminder_ReturnReminderResponse() {
        //arrange
        User user = User.builder()
                .id(1L)
                .username("Maksim")
                .email("maksim@gmail.com")
                .telegramId("12345")
                .reminders(new ArrayList<>())
                .build();

        Reminder firstReminder = Reminder.builder()
                .id(1L)
                .title("Проверить почту")
                .description("Проверить и отсортировать по папкам")
                .remind(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();

        Reminder secondReminder = Reminder.builder()
                .id(2L)
                .title("Поставить пирог в духовку")
                .description("Поставить на 30 минут")
                .remind(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();

        user.getReminders().add(firstReminder);
        user.getReminders().add(secondReminder);

        Long userId = 1L;
        Long reminderId = 1L;

        when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        //act
        ReminderResponse response = service.deleteReminder(userId, reminderId);

        //assert
        assertEquals("Напоминание с Id: " + reminderId + " было удалено", response.message());
        assertEquals(1, user.getReminders().size());
    }


    @Test
    public void ReminderService_UpdateReminder_ReturnUserNotFoundException() {
        //arrange
        Long userId = -1L;
        Long reminderId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //act
        UserNotFoundException ex = assertThrows(
                UserNotFoundException.class,
                () -> service.updateReminder(reminderId, ReminderUpdateRequest.builder().userId(userId).build()));

        //assert
        assertEquals("Пользователь не найден", ex.getMessage());
    }


    @Test
    public void ReminderService_UpdateReminder_ReturnReminderException() {
        //arrange
        User user = User.builder()
                .id(1L)
                .username("Maksim")
                .email("maksim@gmail.com")
                .telegramId("12345")
                .reminders(new ArrayList<>())
                .build();

        when(userRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(user));

        //act
        ReminderException ex = assertThrows(
                ReminderException.class,
                () -> service.updateReminder(user.getId(), ReminderUpdateRequest.builder().userId(user.getId()).build()));

        //assert
        assertEquals("Напоминание не найдено", ex.getMessage());
    }


    @Test
    public void ReminderService_UpdateReminder_ReturnReminderResponse() {
        //arrange
        User user = User.builder()
                .id(1L)
                .username("Maksim")
                .email("maksim@gmail.com")
                .telegramId("12345")
                .reminders(new ArrayList<>())
                .build();

        Reminder reminder = Reminder.builder()
                .id(1L)
                .title("Проверить почту")
                .description("Проверить и отсортировать по папкам")
                .remind(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();

        user.getReminders().add(reminder);

        ReminderUpdateRequest newReminder = ReminderUpdateRequest.builder()
                .title("Проверить почту")
                .description("Проверить и отсортировать по папкам, а также очистить папку спам")
                .remind(LocalDateTime.now().plusMinutes(10))
                .userId(user.getId())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mapper.updateToEntity(user.getReminders().get(0), newReminder)).thenReturn(reminder);

        when(reminderRepository.save(reminder)).thenReturn(reminder);

        //act
        ReminderResponse response = service.updateReminder(reminder.getId(), newReminder);

        //assert
        assertEquals("Обновление напоминания прошло успешно", response.message());



    }
}
