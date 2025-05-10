package com.notes_api_service.service.user_impl;

import com.notes_api_service.dto.PasswordChngRequest;
import com.notes_api_service.dto.PasswordResetReq;
import com.notes_api_service.entity.EmailRequest;
import com.notes_api_service.entity.User;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import com.notes_api_service.repository.UserRepository;
import com.notes_api_service.service.EmailService;
import com.notes_api_service.service.UserService;
import com.notes_api_service.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public void changePassword(PasswordChngRequest passwordChngRequest) {
        User loggedInuser = CommonUtil.getLogedInUser();
        if (!passwordEncoder.matches( passwordChngRequest.getOldPassword(),loggedInuser.getPassword())) {
            throw new IllegalArgumentException("Old password does not match");
        }
        loggedInuser.setPassword(passwordEncoder.encode(passwordChngRequest.getNewPassword()));
        userRepository.save(loggedInuser);
    }



    @Override
    public void sendEmailPasswordReset(String email,String url) throws Exception {
       User user =  userRepository.findByEmail(email);
       if (ObjectUtils.isEmpty(user)){
           throw new ResourceNotFoundException("Invalid Email ");
       }
       //generate password reset token
        String passwordResetToken = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(passwordResetToken);
        User updateUser = userRepository.save(user);
        //send email for reset password
       emailSendForRegister(updateUser,url);

    }

    @Override
    public void resetPassword(PasswordResetReq passwordResetReq) throws Exception {
        User user = userRepository
                .findById(passwordResetReq.getUid())
                .orElseThrow(()->new ResourceNotFoundException("Invalid User"));
        user.setPassword(passwordEncoder.encode(passwordResetReq.getNewPassword()));
        user.getStatus().setPasswordResetToken(null);
        userRepository.save(user);
    }

    private void emailSendForRegister(User user ,String url) throws Exception {
        String resetToken = (user.getStatus() != null) ?
                user.getStatus().getPasswordResetToken() : "null";
        String verificationLink = String.format("%s/api/v1/home/verify-pswd-link?uid=%d&code=%s",url,
                user.getId(), resetToken);

        String message = String.format(
                "<html>"
                        + "<body>"
                        + "<p>Hi, <b>%s %s</b>,</p>"
                        + "<p>You have requested to reset your password.</p>"
                        + "<p>Click the link below to reset your password:</p>"
                        + "<p><a href='%s' style='background-color:blue;color:white;padding:10px 15px;text-decoration:none;'>Verify Account</a></p>"
                        + "<br>"
                        +"<p style='color:red;'>Note: Ignore this email if you do remember your password or you have not made this request.</p>"
                        + "<br>"
                        + "<p>Thanks,</p>"
                        + "<p><b>Notesapi.com</b></p>"
                        + "</body>"
                        + "</html>",
                user.getFirstName(),
                user.getLastName(),
                verificationLink
        );
        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .subject("Password Reset Confirmation Link")
                .title("Password Reset")
                .message(message)
                .build();
        //send password reset to user
        emailService.sendEmail(emailRequest);

    }

}
