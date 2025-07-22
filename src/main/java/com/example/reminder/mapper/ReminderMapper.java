package com.example.reminder.mapper;

import com.example.reminder.dto.request.ReminderRequest;
import com.example.reminder.dto.request.ReminderUpdateRequest;
import com.example.reminder.dto.response.FullReminderResponse;
import com.example.reminder.entity.Reminder;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReminderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userId", target = "user.id")
    Reminder toEntity(ReminderRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "id", ignore = true)
    Reminder updateToEntity(@MappingTarget Reminder entity, ReminderUpdateRequest dto);

    @BeanMapping(ignoreUnmappedSourceProperties = "id")
    FullReminderResponse toDto(Reminder entity);

    List<FullReminderResponse> toDtoList(List<Reminder> entities);

    default Page<FullReminderResponse> toDtoPage(Page<Reminder> page) {
        List<FullReminderResponse> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }
}
