package com.notes_api_service.exception.customException;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String message) {
        super( message);
    }
}
