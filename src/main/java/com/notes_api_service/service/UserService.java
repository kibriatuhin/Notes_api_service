package com.notes_api_service.service;

import com.notes_api_service.dto.UserDto;

public interface UserService {
    Boolean registerUser(UserDto userDto);
}
