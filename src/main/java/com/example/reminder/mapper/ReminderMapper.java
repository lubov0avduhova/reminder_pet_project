package com.example.reminder.mapper;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.entity.Reminder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReminderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Reminder toEntity(ReminderRequest dto);


}
