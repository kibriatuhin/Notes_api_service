package com.notes_api_service.utils;

import com.notes_api_service.handler.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {
    public static ResponseEntity<?> createBuildResponse(Object data , HttpStatus status){
        GenericResponse genericResponse = GenericResponse.builder()
                .responseStatus(status).status("success").message("success").data(data)
                .build();
       return   genericResponse.createResponseEntity();
    }

    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status){
        GenericResponse genericResponse = GenericResponse.builder()
                .responseStatus(status).status("success").message(message)
                .build();
        return   genericResponse.createResponseEntity();
    }

    public static ResponseEntity<?> createErrorResponse(Object data , HttpStatus status){
        GenericResponse genericResponse = GenericResponse.builder()
                .responseStatus(status).status("Failed").message("Failed").data(data)
                .build();
        return   genericResponse.createResponseEntity();
    }
    public static ResponseEntity<?> createErrorResponseMessage(String message , HttpStatus status){
        GenericResponse genericResponse = GenericResponse.builder()
                .responseStatus(status).status("Failed").message(message)
                .build();
        return   genericResponse.createResponseEntity();
    }


}
