package com.example.reminder.service;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.response.ReminderResponse;
import com.example.reminder.entity.Reminder;
import com.example.reminder.exception.ReminderException;
import com.example.reminder.exception.UserNotFoundException;
import com.example.reminder.mapper.ReminderMapper;
import com.example.reminder.repository.ReminderRepository;
import com.example.reminder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderService implements IReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final ReminderMapper mapper;

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

    private void existsUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }
}