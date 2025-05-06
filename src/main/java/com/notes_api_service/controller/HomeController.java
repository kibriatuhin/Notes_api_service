package com.notes_api_service.controller;

import com.notes_api_service.dto.PasswordResetReq;
import com.notes_api_service.service.HomeService;
import com.notes_api_service.service.UserService;
import com.notes_api_service.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid , @RequestParam String code) throws Exception{

       return homeService.verifyAccount(uid,code)?
               CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK) :
               CommonUtil.createErrorResponseMessage("Account verification failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email,HttpServletRequest servletRequest) throws Exception {
        String url =  CommonUtil.getUrl(servletRequest);
        userService.sendEmailPasswordReset(email,url);
        return CommonUtil.createBuildResponseMessage("Email Send Success !! Check Email for Reset password", HttpStatus.OK);
    }
    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception {
        return homeService.verifyPswdResetLink(uid,code)?
                CommonUtil.createBuildResponseMessage("Verification success", HttpStatus.OK) :
                CommonUtil.createErrorResponseMessage("verification failed", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/reset-pswd")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetReq passwordResetReq) throws Exception {
        userService.resetPassword(passwordResetReq);
        return CommonUtil.createBuildResponseMessage("Password Reset Success !", HttpStatus.OK);
    }

}
