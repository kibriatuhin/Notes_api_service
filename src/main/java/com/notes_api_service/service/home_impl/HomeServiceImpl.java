package com.notes_api_service.service.home_impl;

import com.notes_api_service.entity.AccountStatus;
import com.notes_api_service.entity.User;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import com.notes_api_service.exception.customException.SuccessException;
import com.notes_api_service.repository.UserRepository;
import com.notes_api_service.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid User"));
        if (user.getStatus().getVerificationCode() == null){
             throw new SuccessException("Account Already Verified");
        }
        AccountStatus status = user.getStatus();
        if (user.getStatus().getVerificationCode().equals(verificationCode)){
            status.setIsActive(true);
            status.setVerificationCode(null);
            //user.setStatus(status);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public Boolean verifyPswdResetLink(Integer userId, String verificationCode) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid User"));
        verifyPasswordResetToken(user.getStatus().getPasswordResetToken(),verificationCode);
        return true;
    }

    private void verifyPasswordResetToken(String existToken, String reqToken) {
        //request token not null
        if (StringUtils.hasText(reqToken)){
            //password already reset
            if (!StringUtils.hasText(existToken)){
                throw new SuccessException("ALready Password reset");
            }
            //user requested token changes
            if (!existToken.equals(reqToken)){
                throw new IllegalArgumentException("Invalid url");
            }

        }else {
            throw new IllegalArgumentException("Invalid Request Token");
        }
    }
}
