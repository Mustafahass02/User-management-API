package com.examplemyproject.demo.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.examplemyproject.demo.entity.Users;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

    private static final String SECRET_KEY = "ThisIsAStrongSecretKeyForJWT256BitEncryption";
    private static final long EXPIRATION_TIME = 1000 * 60 * 15;


    public String generateToken(Users user){
         Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
               .setSubject(user.getEmail())
               .claim("Role", user.getRole())
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
               .signWith(key, SignatureAlgorithm.HS256)
               .compact();
     
     
}

private Key getSigniKey(){
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
}

public String extractUsername(String token){
    return Jwts
            .parserBuilder()
            .setSigningKey(getSigniKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
              

}



}
