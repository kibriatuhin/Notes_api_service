package com.notes_api_service.service;

import com.notes_api_service.dto.PasswordChngRequest;
import com.notes_api_service.dto.PasswordResetReq;
import com.notes_api_service.exception.customException.ResourceNotFoundException;

public interface UserService {
    void changePassword(PasswordChngRequest passwordChngRequest);
    void sendEmailPasswordReset(String email,String url) throws Exception;
    void resetPassword(PasswordResetReq passwordChngRequest) throws Exception;
}
