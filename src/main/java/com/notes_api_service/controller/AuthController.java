package com.notes_api_service.controller;

import com.notes_api_service.dto.LoginRequest;
import com.notes_api_service.dto.LoginResponse;
import com.notes_api_service.dto.UserRequestDto;
import com.notes_api_service.service.AuthService;
import com.notes_api_service.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/save")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest servletRequest) throws Exception {
        log.info("AuthController :: registerUser :: Execution start");
        String url =  CommonUtil.getUrl(servletRequest);
        Boolean register = authService.registerUser(userRequestDto,url);
        ResponseEntity<?> response = register ? CommonUtil.createBuildResponseMessage("Register success", HttpStatus.CREATED)
                : CommonUtil.createErrorResponseMessage(" Register Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        log.info("AuthController :: registerUser :: Execution end");
       return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        log.info("AuthController :: loginUser :: Execution start");
        LoginResponse loginResponse = authService.loginUser(loginRequest);
        if (ObjectUtils.isEmpty(loginResponse)) {
            log.info("Error :: {} " ,"Login Failed");
            return CommonUtil.createErrorResponseMessage(" Invalid Credential", HttpStatus.BAD_REQUEST);
        }
        log.info("AuthController :: loginUser :: Execution end");
        return CommonUtil.createBuildResponse(loginResponse, HttpStatus.CREATED);
    }



}
