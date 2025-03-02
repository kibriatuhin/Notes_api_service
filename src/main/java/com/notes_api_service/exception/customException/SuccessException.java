package com.notes_api_service.exception.customException;

public class SuccessException extends RuntimeException{
    public SuccessException(String message) {
        super(message);
    }
}
