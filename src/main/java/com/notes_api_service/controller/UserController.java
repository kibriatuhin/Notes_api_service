package com.notes_api_service.controller;

import com.notes_api_service.dto.PasswordChngRequest;
import com.notes_api_service.dto.UserRequestDto;
import com.notes_api_service.dto.UserResponseDto;
import com.notes_api_service.entity.User;
import com.notes_api_service.service.UserService;
import com.notes_api_service.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userDtl")
public class UserController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        User logedInUser = CommonUtil.getLogedInUser();
        UserResponseDto userRequestDto =  modelMapper.map(logedInUser, UserResponseDto.class);
        return CommonUtil.createBuildResponse(userRequestDto, HttpStatus.OK);
    }

    @PostMapping("/changePswd")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChngRequest passwordChngRequest){
        userService.changePassword(passwordChngRequest);
        return CommonUtil.createBuildResponseMessage("Password Change Success", HttpStatus.OK);
    }

}
