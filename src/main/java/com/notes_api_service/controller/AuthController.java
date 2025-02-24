package com.notes_api_service.controller;

import com.notes_api_service.dto.UserDto;
import com.notes_api_service.service.UserService;
import com.notes_api_service.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) throws Exception {
        Boolean register = userService.registerUser(userDto);
       return register ? CommonUtil.createBuildResponseMessage("Register success", HttpStatus.CREATED)
               : CommonUtil.createErrorResponseMessage(" Register Failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
