package com.notes_api_service.service.home_impl;

import com.notes_api_service.entity.AccountStatus;
import com.notes_api_service.entity.User;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import com.notes_api_service.exception.customException.SuccessException;
import com.notes_api_service.repository.UserRepository;
import com.notes_api_service.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
