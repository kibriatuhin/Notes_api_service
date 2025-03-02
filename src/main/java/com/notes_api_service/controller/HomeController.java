package com.notes_api_service.controller;

import com.notes_api_service.service.HomeService;
import com.notes_api_service.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid , @RequestParam String code) throws Exception{

       return homeService.verifyAccount(uid,code)?
               CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK) :
               CommonUtil.createErrorResponseMessage("Account verification failed", HttpStatus.BAD_REQUEST);
    }


}
