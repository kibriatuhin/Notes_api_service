package com.notes_api_service.service;

import com.notes_api_service.dto.TodoDto;
import com.notes_api_service.exception.customException.ResourceNotFoundException;

import java.util.List;

public interface TodoService {
    Boolean saveTodo(TodoDto todoDto) throws Exception;
    TodoDto getTodoById(Integer id) throws Exception;
    List<TodoDto> getTodoByUser();
}
