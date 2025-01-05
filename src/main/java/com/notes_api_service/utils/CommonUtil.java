package com.notes_api_service.utils;

import com.notes_api_service.handler.GenericResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    public static String getContentType(String orginalFileName){
        String extention = FilenameUtils.getExtension(orginalFileName);
        switch (extention){
            case "pdf":
                return  "application/pdf";
            case "xlsx", "xls":
                return  "application/vnd.ms-excel";
            case "txt":
                return "text/plain";
            case "png":
                return  "image/png";
            case "jpeg", "jpg":
                return  "image/jpeg";
            default:
                return  "application/octet-stream";
        }

    }


}
