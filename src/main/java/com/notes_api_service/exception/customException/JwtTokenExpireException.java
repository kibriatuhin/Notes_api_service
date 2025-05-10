package com.notes_api_service.exception.customException;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtTokenExpireException extends RuntimeException {
    public JwtTokenExpireException(String message) {
        super(message);
    }

}
