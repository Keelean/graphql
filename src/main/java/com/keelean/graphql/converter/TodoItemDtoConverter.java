package com.keelean.graphql.converter;

import com.keelean.graphql.dto.TodoItemDto;
import com.keelean.graphql.entity.TodoItem;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TodoItemDtoConverter implements Converter<TodoItem, TodoItemDto> {

    @Override
    public @NonNull TodoItemDto convert(TodoItem source) {
        return new TodoItemDto(source.getId(), source.getNotes(), source.getDueDate(), source.isMarkAsComplete(), source.getCompletionDate(), source.getCreatedAt(), source.getUpdatedAt());
    }
}
