package com.keelean.graphql.converter;

import com.keelean.graphql.dto.TodoDto;
import com.keelean.graphql.entity.Todo;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;

@Component
public class TodoConverter implements Converter<TodoDto, Todo> {

    @Override
    public @NonNull Todo convert(TodoDto source) {
        return new Todo(0, source.getTitle(), source.isMarkAsComplete(), source.isMarkAsComplete() ? LocalDate.now() : null, Collections.emptyList());
    }
}
