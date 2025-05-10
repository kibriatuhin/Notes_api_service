package com.notes_api_service.service.jwt_impl;

import com.notes_api_service.entity.User;
import com.notes_api_service.exception.customException.JwtAuthenticationException;
import com.notes_api_service.exception.customException.JwtTokenExpireException;
import com.notes_api_service.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private String secretKey="";

    public JwtServiceImpl() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sKey = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sKey.getEncoded());

        }catch (Exception e){
            e.printStackTrace();
        }
        //this.secretKey = secretKey;
    }

    @Override
    public String generateJwtToken(User user) {
        Map<String ,Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("role",user.getRoles());
        claims.put("status",user.getStatus().getIsActive());

       String token = Jwts.builder()
               .setClaims(claims)
               .setSubject(user.getEmail())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
               .signWith(getKey(), SignatureAlgorithm.HS256)
               .compact();

        return token;
    }

    @Override
    public String extractUserNameFromJwtToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    @Override
    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        String username = extractUserNameFromJwtToken(token);

        if (username.equalsIgnoreCase(userDetails.getUsername())
                && !isTokenExpired(token)) {
            return true;
        }
        return false;
    }
    private Boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        try{
            return Jwts.parser().verifyWith(decriptKey(secretKey))
                    .build().parseSignedClaims(token).getPayload();
        }catch (ExpiredJwtException e){
            throw new JwtTokenExpireException("Token has expired");
        }catch (JwtException e){
            throw new JwtAuthenticationException("Invalid JWT token");
        }catch (Exception e){
            throw e;
        }
    }
    private SecretKey decriptKey(String secretKey){
       byte[] key =   Decoders.BASE64.decode(secretKey);
        return  Keys.hmacShaKeyFor(key);
    }


    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}

