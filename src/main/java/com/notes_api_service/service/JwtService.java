package com.notes_api_service.service;

import com.notes_api_service.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateJwtToken(User user);
    String extractUserNameFromJwtToken(String token);
    Boolean validateJwtToken(String token, UserDetails userDetails);
}
