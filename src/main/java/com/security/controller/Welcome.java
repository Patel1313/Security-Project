package com.security.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/")
    public ResponseEntity<String> welcomeMessage() {
        return new ResponseEntity<>("Hello, Welcome to "+applicationName.toUpperCase()+" Application.", HttpStatus.OK);
    }

}
