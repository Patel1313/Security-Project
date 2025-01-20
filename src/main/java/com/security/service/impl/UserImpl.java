package com.security.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.customsecurity.JwtService;
import com.security.dto.LogInInput;
import com.security.dto.UserDto;
import com.security.dto.UserInput;
import com.security.entity.User;
import com.security.exception.UserNotFound;
import com.security.repo.UserRepo;
import com.security.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImpl implements UserService {

    private final UserRepo userRepo;
    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserImpl(UserRepo userRepo, ObjectMapper objectMapper, BCryptPasswordEncoder encoder, JwtService jwtService, AuthenticationManager authenticationManager)
    {
        this.userRepo = userRepo;
        this.objectMapper = objectMapper;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto findByEmail(String email) throws UserNotFound {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UserNotFound(String.format("Email :  %s Not Found.", email));
        }
        return convertEntityToDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepo.findAll().stream().map(this::convertEntityToDto).toList();
    }

    @Override
    public UserDto addUser(UserInput user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return convertEntityToDto(userRepo.save(convertEntityToDto(user)));
    }

//    @Override
//    public UserDto login(LogInInput logInInput) throws UserNotFound {
//        logInInput.setPassword(encoder.encode(logInInput.getPassword()));
//        User user = userRepo.findByEmailAndPassword(logInInput.getEmail(), logInInput.getPassword()).orElseThrow(() -> new UserNotFound("Login Details are Not Found."));
//        UserDto userDto = convertEntityToDto(user);
//        userDto.setToken("QAZXSWEDCVFRFVCEWSXZAQWERTYUIOPLMNBVCXZASDFGHJKIUYTREWQAZQ!@#$%^&*()$#@!@#ED");
//        return userDto;
//    }

    @Override
    public UserDto login(LogInInput logInInput) throws UserNotFound {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInInput.getEmail(), logInInput.getPassword()
                ));
        if (authenticate.isAuthenticated()) {
            String token = jwtService.generateToken(logInInput);
            return new UserDto(0, authenticate.getName(), authenticate.getName(), token);
        }
        else {
            throw new UserNotFound("Failed.");
        }
    }

    private UserDto convertEntityToDto(User user) {
        return objectMapper.convertValue(user, UserDto.class);
    }

    private User convertEntityToDto(UserInput userInput) {
        return objectMapper.convertValue(userInput, User.class);
    }

}
