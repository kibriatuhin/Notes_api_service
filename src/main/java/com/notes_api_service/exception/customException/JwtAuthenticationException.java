package com.notes_api_service.exception.customException;

public class JwtAuthenticationException extends RuntimeException{
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
