package com.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.dto.LogInInput;
import com.security.dto.UserDto;
import com.security.dto.UserInput;
import com.security.entity.User;
import com.security.exception.UserNotFound;
import com.security.repo.UserRepo;
import com.security.service.UserService;
import com.security.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements UserService {

    private final UserRepo userRepo;
    private final ObjectMapper objectMapper;
    private final Utils utils;

    public UserImpl(UserRepo userRepo, ObjectMapper objectMapper, Utils utils) {
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
        this.utils = utils;
    }

    @Override
    public UserDto findByEmail(String email) throws UserNotFound {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UserNotFound(String.format("Email :  %s Not Found.", email)));
        return convertEntityToDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepo.findAll().stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public UserDto addUser(UserInput user) {
        user.setPassword(utils.enCoding(user.getPassword()));
        return convertEntityToDto(userRepo.save(convertEntityToDto(user)));
    }

    @Override
    public UserDto login(LogInInput logInInput) throws UserNotFound {
        logInInput.setPassword(utils.enCoding(logInInput.getPassword()));
        User user = userRepo.findByEmailAndPassword(logInInput.getEmail(), logInInput.getPassword()).orElseThrow(() -> new UserNotFound("Login Details are Not Found."));
        return convertEntityToDto(user);
    }

    private UserDto convertEntityToDto(User user) {
        return objectMapper.convertValue(user, UserDto.class);
    }

    private User convertEntityToDto(UserInput userInput) {
        return objectMapper.convertValue(userInput, User.class);
    }

}
