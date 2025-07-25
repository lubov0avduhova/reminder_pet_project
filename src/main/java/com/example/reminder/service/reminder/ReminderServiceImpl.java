package com.example.reminder.service.reminder;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.request.ReminderUpdateRequest;
import com.example.reminder.dto.response.FullReminderResponse;
import com.example.reminder.dto.response.ReminderResponse;
import com.example.reminder.entity.Reminder;
import com.example.reminder.entity.User;
import com.example.reminder.exception.ReminderException;
import com.example.reminder.exception.UserNotFoundException;
import com.example.reminder.mapper.ReminderMapper;
import com.example.reminder.repository.ReminderRepository;
import com.example.reminder.repository.UserRepository;
import com.example.reminder.service.notification.NotificationService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final ReminderMapper mapper;
    private NotificationService notificationService;

    @Transactional
    @Override
    public ReminderResponse createReminder(ReminderRequest reminder) {
        existsUserById(reminder.userId());
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
        UserWithReminder userWithReminder = findUserWithReminder(userId, reminderId);

        userWithReminder.user.getReminders().remove(userWithReminder.reminder);

        return new ReminderResponse("Напоминание с Id: " + userWithReminder.reminder.getId() + " было удалено");
    }

    @Transactional
    @Override
    public ReminderResponse updateReminder(@NotNull Long reminderId, ReminderUpdateRequest reminder) {

        UserWithReminder userWithReminder = findUserWithReminder(reminder.userId(), reminderId);

        Reminder newEntity = mapper.updateToEntity(userWithReminder.reminder, reminder);
        reminderRepository.save(newEntity);

        return new ReminderResponse("Обновление напоминания прошло успешно");
    }

    @Override
    public FullReminderResponse findReminder(String title, String description, LocalDateTime date) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        if (date != null) {
            start = date.toLocalDate().atStartOfDay();
            end = date.toLocalDate().atTime(LocalTime.MAX);
        }

        Reminder reminder = reminderRepository.findReminder(title, description, start, end);
        if (reminder == null) {
            throw new ReminderException("Напоминание не найдено");
        }

        return mapper.toDto(reminder);
    }

    @Override
    public Page<FullReminderResponse> findAllRemindersBySort(Pageable pageable) {
        Page<Reminder> sorted = reminderRepository.findAll(pageable);

        return mapper.toDtoPage(sorted);
    }

    @Override
    public Page<FullReminderResponse> findAllRemindersByFilter(LocalDateTime remindAfter, LocalDateTime remindBefore, Pageable pageable) {
        Page<Reminder> allByRemindBetween = reminderRepository.findAllByRemindBetween(remindAfter, remindBefore, pageable);

        return mapper.toDtoPage(allByRemindBetween);
    }

    @Transactional
    @Override
    public void checkDueReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusMinutes(15);
        Pageable pageable = PageRequest.of(0, 100); //todo не знаю, какое тут число ставить
        Page<Reminder> page;

        do {
            page = reminderRepository.findAllByRemindBetweenAndSentFalse(windowStart, now, pageable);
            for (Reminder reminder : page.getContent()) {
                notificationService.send(reminder);
                reminderRepository.updateSentTrueById(reminder.getId());
            }
            pageable = pageable.next();
        } while (!page.isLast());
    }

    private void existsUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    private record UserWithReminder(User user, Reminder reminder) {
    }

    private UserWithReminder findUserWithReminder(Long userId, Long reminderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        Reminder reminder = user.getReminders().stream()
                .filter(r -> r.getId().equals(reminderId))
                .findFirst()
                .orElseThrow(() -> new ReminderException("Напоминание не найдено"));

        return new UserWithReminder(user, reminder);
    }

}