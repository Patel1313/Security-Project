package com.security.customsecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JwtServiceCustom
{
    public static final String SECRET = "1234567890098765432112345678900987654321123456789098765345664223456";
    public String generateToken(String email){
        Map<String, Object> claims = new HashMap<>();
        claims.put("ROLE","USER");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuer("BB")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+100*100*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token).getBody();
    }
    public String getEmail(String token){
        return getAllClaims(token).getSubject();
    }
    private Date expireDate(String token){
        return getAllClaims(token)
                .getExpiration();
    }

    private boolean isTokenExpired(String token){
        return expireDate(token).before(new Date());
    }

    public boolean isValid(String token, UserDetails userDetails){
        String email = getEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
