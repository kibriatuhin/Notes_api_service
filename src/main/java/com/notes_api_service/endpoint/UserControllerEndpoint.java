package com.notes_api_service.endpoint;

import com.notes_api_service.dto.PasswordChngRequest;
import com.notes_api_service.dto.UserResponseDto;
import com.notes_api_service.entity.User;
import com.notes_api_service.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/userDtl")
@Tag(name = "User APIs", description = "Authentication user operation APIs")
public interface UserControllerEndpoint {


    @GetMapping("/profile")
    @Operation(summary = "get user details" ,tags = {"User APIs"})
    public ResponseEntity<?> getProfile();


    @PostMapping("/changePswd")
    @Operation(summary = "user password changed",tags = {"User APIs"})
    public ResponseEntity<?> changePassword(@RequestBody PasswordChngRequest passwordChngRequest);
}
