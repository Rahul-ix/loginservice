package com.login.service;

import com.login.dto.LoginDto;
import com.login.entity.Login;
import com.login.repository.LoginRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class Services {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSenderServices javaMailSenderServices;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private JWTService jwtService;

    Random random = new Random();
    int otp = 100000 + random.nextInt(900000);
    String otps = String.valueOf(otp);

    public String loginUser(LoginDto loginDto) {

        //convert dto to entity
        Login login = convertEntity(loginDto);

//        String userid = loginDto.getUserid();
//        String mobile = loginDto.getMobile();
        Optional<Login> byUserid = loginRepository.findByUserid(login.getUserid());
        Optional<Login> byMobile = loginRepository.findByMobile(login.getMobile());

        if (byUserid.isEmpty() && byMobile.isEmpty()) {
            System.out.println("Invalid data");
            return null;
        }

        //convert entity to dto
        LoginDto loginDtos = convertDto(login);
        String toEmail = "rahulkalal676@gmail.com";
        String subject = "Your NetBanking OTP Code";
        // generates 100000 to 999999
        String body = "Dear user,\n\nYour OTP is: " + otp + "\n\nValid for 5 minutes.";
        javaMailSenderServices.sendOtpMail(toEmail, subject, body);

        String token = jwtService.generateToken(loginDtos.getUserid());
//        if (token!=null && token.startsWith(""))

        return token;
    }

    private LoginDto convertDto(Login login) {
        return modelMapper.map(login, LoginDto.class);
    }

    private Login convertEntity(LoginDto loginDto) {
        return modelMapper.map(loginDto, Login.class);
    }

   public String validateOtp(String myOtp){


        if (!myOtp.equals(otps)){
            return "Invalid OTP";
        }
        otps = null;
        return "Validated OTP";
    }
}
