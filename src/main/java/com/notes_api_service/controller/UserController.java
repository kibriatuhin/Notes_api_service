package com.notes_api_service.controller;

import com.notes_api_service.dto.PasswordChngRequest;
import com.notes_api_service.dto.UserRequestDto;
import com.notes_api_service.dto.UserResponseDto;
import com.notes_api_service.endpoint.UserControllerEndpoint;
import com.notes_api_service.entity.User;
import com.notes_api_service.service.UserService;
import com.notes_api_service.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController

public class UserController implements UserControllerEndpoint {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;


    @Override
    public ResponseEntity<?> getProfile(){
        log.info("UserController :: getProfile :: Execution start");
        User logedInUser = CommonUtil.getLogedInUser();
        UserResponseDto userRequestDto =  modelMapper.map(logedInUser, UserResponseDto.class);
        log.info("UserController :: getProfile :: Execution end");
        return CommonUtil.createBuildResponse(userRequestDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(PasswordChngRequest passwordChngRequest){
        userService.changePassword(passwordChngRequest);
        return CommonUtil.createBuildResponseMessage("Password Change Success", HttpStatus.OK);
    }

}
