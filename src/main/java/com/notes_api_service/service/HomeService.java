package com.notes_api_service.service;

public interface HomeService {
    Boolean verifyAccount(Integer userId , String verificationCode) throws Exception;
    Boolean verifyPswdResetLink(Integer userId , String verificationCode) throws Exception;
}
