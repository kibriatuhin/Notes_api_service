package com.notes_api_service.exception.customException;

import java.util.Map;

public class DtoValidationException extends RuntimeException{
    private final Map<String,Object> errors;
    public DtoValidationException(Map<String,Object> errors){
        super("Validation Failed");
        this.errors = errors;
    }
    public Map<String,Object> getErrors(){
        return errors;
    }

}
