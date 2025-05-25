package com.notes_api_service.exception;

import com.notes_api_service.exception.customException.*;
import com.notes_api_service.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("GlobalExceptionHandler :: handleException() :: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e) {
        log.error("GlobalExceptionHandler :: handleNullPointerException() :: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
        log.error("GlobalExceptionHandler :: handleResourceNotFoundException() :: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("GlobalExceptionHandler :: handleMethodArgumentNotValidException() :: {}", e.getMessage());
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getAllErrors().forEach(er -> {
             String sms = er.getDefaultMessage();
             String fieldName = ((FieldError) er).getField();
             errors.put(fieldName, sms);
         });

        return CommonUtil.createErrorResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DtoValidationException.class)
    public ResponseEntity<?> handleDtoValidationException(DtoValidationException e) {
        log.error("GlobalExceptionHandler :: handleDtoValidationException() :: {}", e.getMessage());
        return CommonUtil.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDataException(ExistDataException e) {
        log.error("GlobalExceptionHandler :: handleExistDataException() :: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("GlobalExceptionHandler :: handleHttpMessageNotReadableException() :: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        log.error("GlobalExceptionHandler :: handleMaxSizeException() :: {}", exc.getMessage());
        return CommonUtil.createErrorResponseMessage(exc.getMessage(),HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException exc) {
        log.error("GlobalExceptionHandler :: handleFileNotFoundException() :: {}", exc.getMessage());
        return CommonUtil.createErrorResponseMessage(exc.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException exc) {
        log.error("GlobalExceptionHandler :: handleIllegalArgumentException() :: {}", exc.getMessage());
        return CommonUtil.createErrorResponseMessage(exc.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<?> handleSuccessException(SuccessException e) {
        log.error("GlobalExceptionHandler :: handleSuccessException() :: {}", e.getMessage());
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleJwtAccessDeniedException(AccessDeniedException exc) {
        log.error("GlobalExceptionHandler :: handleJwtAccessDeniedException() :: {}", exc.getMessage());
        return CommonUtil.createErrorResponseMessage(exc.getMessage(),HttpStatus.UNAUTHORIZED);
    }



}
