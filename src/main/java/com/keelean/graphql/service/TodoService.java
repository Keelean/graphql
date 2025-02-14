package com.keelean.graphql.service;

import com.keelean.graphql.converter.TodoConverter;
import com.keelean.graphql.converter.TodoDtoConverter;
import com.keelean.graphql.converter.TodoItemConverter;
import com.keelean.graphql.converter.TodoItemDtoConverter;
import com.keelean.graphql.dto.TodoDto;
import com.keelean.graphql.dto.TodoItemDto;
import com.keelean.graphql.entity.Todo;
import com.keelean.graphql.entity.TodoItem;
import com.keelean.graphql.exception.ResourceNotFoundException;
import com.keelean.graphql.repository.TodoItemRepository;
import com.keelean.graphql.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoItemRepository todoItemRepository;
    private final TodoDtoConverter todoDtoConverter;
    private final TodoConverter todoConverter;
    private final TodoItemDtoConverter todoItemDtoConverter;
    private final TodoItemConverter todoItemConverter;

    public TodoDto addTodo(TodoDto todoDto) {
        Todo todo = todoConverter.convert(todoDto);
        Todo savedTodo = todoRepository.save(todo);

        List<TodoItemDto> todoItemDtoList = todoDto.getItems().stream()
                .map(todoItemConverter::convert)
                .map(todoItem -> {
                    todoItem.setTodo(savedTodo);
                    return todoItemDtoConverter.convert(todoItemRepository.save(todoItem));
                }).toList();

        todoDto = todoDtoConverter.convert(savedTodo);
        todoDto.setItems(todoItemDtoList);
        return todoDto;
    }

    public List<TodoItemDto> addTodoItem(long todoId, List<TodoItemDto> todoItemDtoList) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo with id '" + todoId + "'  not found"));

        return todoItemDtoList.stream().map(todoItemDto -> {
            TodoItem todoItem = todoItemConverter.convert(todoItemDto);
            todoItem.setTodo(todo);
            todoItem = todoItemRepository.save(todoItem);
            return todoItemDtoConverter.convert(todoItem);
        }).toList();
    }

    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll().stream().map(todoDtoConverter::convert).toList();
    }

    public TodoItemDto updateTodoItem(TodoItemDto todoItemDto) {
        if (!todoItemRepository.existsById(todoItemDto.getId())) {
            throw new ResourceNotFoundException("Todo item with id '" + todoItemDto.getId() + "'  not found");
        }
        todoItemRepository.save(todoItemConverter.convert(todoItemDto));
        return todoItemDto;
    }

}
