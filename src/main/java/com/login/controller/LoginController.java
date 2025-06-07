package com.login.controller;

import com.login.dto.LoginDto;
import com.login.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app/v1")
public class LoginController {

    @Autowired
    private Services services;


    // http://localhost:8080/api/app/v1/login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto){
        String jwtToken = services.loginUser(loginDto);

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);

    }

    @PostMapping("/validateotp")
    public ResponseEntity<String> validateOtp(@RequestParam String otp){
        String validatedOtp = services.validateOtp(otp);
        return new ResponseEntity<>(validatedOtp,HttpStatus.OK);
    }

    @PostMapping("/viewbalance")
    public ResponseEntity<String> viewBalance(){
        return new ResponseEntity<>("View Balance",HttpStatus.OK);
    }

}
