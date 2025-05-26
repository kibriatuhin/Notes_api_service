package com.notes_api_service.controller;

import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.dto.TodoDto;
import com.notes_api_service.endpoint.TodoControllerEndpoint;
import com.notes_api_service.service.TodoService;
import com.notes_api_service.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
public class TodoController implements TodoControllerEndpoint {
    @Autowired
    TodoService todoService;

    @Override
    public ResponseEntity<?> saveNotes(TodoDto todoDto) throws Exception {

       return todoService.saveTodo(todoDto) ?
                CommonUtil.createBuildResponseMessage("Todo saved success", HttpStatus.CREATED)
                : CommonUtil.createErrorResponseMessage("Todo Not  Saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getTodoById(Integer id) throws Exception {
      TodoDto todoDto =   todoService.getTodoById(id);

        return ObjectUtils.isEmpty(todoDto)
                ? CommonUtil.createErrorResponseMessage("Internal Server Error ", HttpStatus.NOT_FOUND)
                : CommonUtil.createBuildResponse(todoDto, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getAllTodoByUser(){

        List<TodoDto> toDoList = todoService.getTodoByUser();
        return CollectionUtils.isEmpty(toDoList)
                ? ResponseEntity.noContent().build()
                : CommonUtil.createBuildResponse(toDoList, HttpStatus.OK);
    }
}
