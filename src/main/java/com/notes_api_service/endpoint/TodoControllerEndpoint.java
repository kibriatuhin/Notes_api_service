package com.notes_api_service.endpoint;

import com.notes_api_service.dto.TodoDto;
import com.notes_api_service.utils.CommonUtil;
import  static com.notes_api_service.utils.Constants.ROLE_ADMIN;
import  static com.notes_api_service.utils.Constants.ROLE_USER;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/todo")
public interface TodoControllerEndpoint {
    @PostMapping("/save")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> saveNotes(@RequestBody TodoDto todoDto) throws Exception ;

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception ;

    @GetMapping("/")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllTodoByUser();
}
