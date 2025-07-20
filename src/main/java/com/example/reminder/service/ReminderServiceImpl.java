package com.example.reminder.service;

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
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final ReminderMapper mapper;

    @Transactional
    @Override
    public ReminderResponse createReminder(ReminderRequest reminder) {
        existsUserById(reminder.getUserId());
        Reminder entity = mapper.toEntity(reminder);
        Reminder savedEntity = reminderRepository.save(entity);

        if (savedEntity.getId() == null) {
            throw new ReminderException("Не удалось сохранить объект");
        }
        return new ReminderResponse("Сохранение напоминания: " + savedEntity.getTitle() + " прошло успешно");
    }

    @Transactional
    @Override
    public ReminderResponse deleteReminder(@NotNull Long userId, @NotNull Long reminderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Reminder reminder = user.getReminders().stream()
                .filter(r -> r.getId().equals(reminderId)).findFirst()
                .orElseThrow(() -> new ReminderException("Напоминание с Id: " + reminderId + " не было удалено. Напоминание не найдено"));


        if (reminderRepository.existsById(reminder.getId())) {
            System.out.println("удален reminder = " + reminder);
            user.getReminders().remove(reminder);
        }


        return new ReminderResponse("Напоминание с Id: " + reminder.getId() + " было удалено");
    }

    @Transactional
    @Override
    public ReminderResponse updateReminder(@NotNull Long reminderId, ReminderUpdateRequest reminder) {

        User user = userRepository.findById(reminder.getUserId()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Reminder oldReminder = user.getReminders().stream().filter(r -> r.getId().equals(reminderId)).findFirst().orElse(null);

        if (oldReminder == null) {
            throw  new ReminderException("Напоминание не найдено");
        }

        Reminder newEntity = mapper.updateToEntity(oldReminder, reminder);
        reminderRepository.save(newEntity);

        return new ReminderResponse("Обновление напоминания прошло успешно");
    }

    private void existsUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}