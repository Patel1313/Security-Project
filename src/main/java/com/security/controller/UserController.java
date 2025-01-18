package com.security.controller;

import com.security.dto.LogInInput;
import com.security.dto.UserInput;
import com.security.exception.UserNotFound;
import com.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email) throws UserNotFound {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserInput user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid LogInInput logInInput) throws UserNotFound {
        return new ResponseEntity<>(userService.login(logInInput), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("welcome")
    public ResponseEntity<String> welcomeMessage() {
        return new ResponseEntity<>("Hi", HttpStatus.OK);
    }
}
