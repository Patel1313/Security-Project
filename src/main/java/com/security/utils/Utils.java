package com.security.utils;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class Utils {

    public String enCoding(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    public String deCoding(String string) {
        return new String(Base64.getDecoder().decode(string));
    }
}
