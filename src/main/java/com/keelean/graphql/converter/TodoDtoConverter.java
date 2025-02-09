package com.keelean.graphql.converter;

import com.keelean.graphql.dto.TodoDto;
import com.keelean.graphql.dto.TodoItemDto;
import com.keelean.graphql.entity.Todo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoDtoConverter implements Converter<Todo, TodoDto> {

    private final TodoItemDtoConverter todoItemDtoConverter;

    @Override
    public @NonNull TodoDto convert(Todo source) {
        List<TodoItemDto> todoItems = source.getItems().stream().map(todoItemDtoConverter::convert).toList();
        return new TodoDto(source.getId(), source.getTitle(), source.isMarkAsComplete(), todoItems);
    }
}
