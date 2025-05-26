package com.notes_api_service.endpoint;

import com.notes_api_service.dto.LoginRequest;
import com.notes_api_service.dto.LoginResponse;
import com.notes_api_service.dto.UserRequestDto;
import com.notes_api_service.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication", description = "All the user authentication APIs")
@RequestMapping("/api/v1/user")
public interface AuthControllerEndpoint {

    @PostMapping("/registration")
    @Operation(summary = "User registration endpoint",tags = {"Authentication"})
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto userRequestDto, HttpServletRequest servletRequest) throws Exception ;


    @PostMapping("/login")
    @Operation(summary = "User login endpoint",tags = {"Authentication"})
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception ;
}
