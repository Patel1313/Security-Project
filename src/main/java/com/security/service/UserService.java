package com.security.service;

import com.security.dto.LogInInput;
import com.security.controller.UserDto;
import com.security.dto.UserInput;
import com.security.exception.UserNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto findByEmail(String email) throws UserNotFound;
    List<UserDto> findAll();
    UserDto addUser(UserInput user);
    UserDto login(LogInInput logInInput) throws UserNotFound;
}
