package com.notes_api_service.endpoint;

import com.notes_api_service.dto.PasswordResetReq;
import com.notes_api_service.utils.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/home")
@Tag(name = "Home APIs", description = "All the home operations APIs")
public interface HomeControllerEndpoint {
    @GetMapping("/verify")
    @Operation(summary = "verify user's account using a verification code",tags = {"Home APIs"})
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid , @RequestParam String code) throws Exception;

    @GetMapping("/send-email-reset")
    @Operation(summary = "send  password reset link to the user registered email address",tags = {"Home APIs"})
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest servletRequest) throws Exception ;

    @GetMapping("/verify-pswd-link")
    @Operation(summary = "Authenticate the password reset request using the provided link",tags = {"Home APIs"})
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception ;

    @PostMapping("/reset-pswd")
    @Operation(summary = "Set a new password for the user following a reset request.",tags = {"Home APIs"})
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetReq passwordResetReq) throws Exception ;
}
