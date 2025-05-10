package com.notes_api_service.service;

import com.notes_api_service.dto.LoginRequest;
import com.notes_api_service.dto.LoginResponse;
import com.notes_api_service.dto.UserRequestDto;

public interface AuthService {
    Boolean registerUser(UserRequestDto userRequestDto, String url) throws Exception;
    LoginResponse loginUser(LoginRequest loginRequest) throws Exception;
}
