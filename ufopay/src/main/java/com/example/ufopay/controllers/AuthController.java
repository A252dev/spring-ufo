package com.example.ufopay.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ufopay.dto.LoginRequest;
import com.example.ufopay.dto.LoginResponse;
import com.example.ufopay.dto.SignUpDto;
import com.example.ufopay.entities.User;
import com.example.ufopay.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // Login to system
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest credentialsDto) {
        return userService.login(credentialsDto);
    }

    // Create the new account
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody SignUpDto signUpDto) {
        User user = userService.register(signUpDto);
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

}
