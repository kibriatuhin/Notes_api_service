package com.notes_api_service.service.todo_impl;

import com.notes_api_service.dto.TodoDto;
import com.notes_api_service.entity.Todo;
import com.notes_api_service.enums.TodoStatus;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import com.notes_api_service.repository.TodoRepository;
import com.notes_api_service.service.TodoService;
import com.notes_api_service.utils.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveTodo(TodoDto todoDto) throws Exception {
        //validation
        validation.todoValidation(todoDto);
        Todo todo = modelMapper.map(todoDto,Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo saveTodo = todoRepository.save(todo);

        return !ObjectUtils.isEmpty(saveTodo);
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception {


        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Todo not found | id invalid"));
        TodoDto todoDto = modelMapper.map(todo,TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    private void setStatus(TodoDto todoDto, Todo todo) {
        for(TodoStatus todoStatus:TodoStatus.values()){
            if (todoStatus.getId().equals(todo.getStatusId())){
                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
                        .id(todoStatus.getId())
                        .name(todoStatus.getName())
                        .build();

                todoDto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId = 1;
        return todoRepository.findByCreatedBy(userId)
                .stream().map(td->modelMapper.map(td,TodoDto.class)).toList();

    }
}
