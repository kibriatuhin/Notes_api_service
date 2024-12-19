package com.notes_api_service.exception.customException;

public class ExistDataException extends RuntimeException{
    public ExistDataException(String message) {
        super(message);
    }
}
