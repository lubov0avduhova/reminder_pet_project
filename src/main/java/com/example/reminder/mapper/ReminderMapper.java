package com.example.reminder.mapper;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.request.ReminderUpdateRequest;
import com.example.reminder.entity.Reminder;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ReminderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Reminder toEntity(ReminderRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "id", ignore = true)
    Reminder updateToEntity(@MappingTarget Reminder entity, ReminderUpdateRequest dto);
}
