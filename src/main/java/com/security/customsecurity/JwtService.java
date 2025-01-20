package com.security.customsecurity;

import com.security.dto.LogInInput;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService
{
    private String secretKey = null;

    public String generateToken(LogInInput logInInput)
    {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims().add(claims)
                .subject(logInInput.getEmail())
                .issuer("BB")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 10 * 100))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey()
    {
        byte[] decode = Decoders.BASE64.decode(getSecretKet());
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKet()
    {
        return "zsyls/EvB98aVb1FesAX+GCZXi+CV3Nz84N6DQ2gnrU=";
    }
}
