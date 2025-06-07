package com.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    @Value("${jwt.key}")
    private String algorithmKey;

    @Value("${jwt.expiry-time}")
    private long expiry;

    @Value("${jwt.issuer}")
    private String issuer;

    private Algorithm algorithm;

    @PostConstruct
    public  void postConstruct(){
        algorithm=Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(String userid){
        //CCIES
        return JWT.create()
                .withClaim("userid",userid)
                .withIssuer(issuer)
                .withExpiresAt(new java.util.Date(System.currentTimeMillis()+expiry))
                .sign(algorithm);

    }

    public String verifyToken(String token){
        //RIBVS
        DecodedJWT decodedJWT= JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim("userid").asString();
    }
}

