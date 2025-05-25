package com.notes_api_service.endpoint;

import com.notes_api_service.dto.PasswordChngRequest;
import com.notes_api_service.dto.UserResponseDto;
import com.notes_api_service.entity.User;
import com.notes_api_service.utils.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/userDtl")
public interface UserControllerEndpoint {
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile();

    @PostMapping("/changePswd")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChngRequest passwordChngRequest);
}
