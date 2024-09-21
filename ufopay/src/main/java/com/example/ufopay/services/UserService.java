package com.example.ufopay.services;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ufopay.dto.LoginRequest;
import com.example.ufopay.dto.LoginResponse;
import com.example.ufopay.dto.SignUpDto;
import com.example.ufopay.entities.User;
import com.example.ufopay.entities.UserBalance;
import com.example.ufopay.exceptions.AppException;
import com.example.ufopay.repositories.UserBalanceRepository;
import com.example.ufopay.repositories.UserRepository;
import com.example.ufopay.services.jwt.UserServiceImpl;
import com.example.ufopay.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserBalanceRepository userBalanceRepository;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userServiceImpl;
    private final JwtUtil jwtUtil;

    public Integer GenerateUserId() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }

    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest credentialsDto) {
        // Check the user in system
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        // Check the password
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()),
                user.getPassword())) {

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(credentialsDto.getEmail(),
                                credentialsDto.getPassword()));
            } catch (BadCredentialsException e) {
                throw new AppException("Incorrect email or password", HttpStatus.NOT_FOUND);
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            UserDetails userDetails;

            // Trying to load details
            try {
                userDetails = userServiceImpl.loadUserByUsername(credentialsDto.getEmail());
            } catch (UsernameNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Create the JWT token with email
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new LoginResponse(jwt));
        } else {
            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
        }

    }

    public User register(SignUpDto signUpDto) {
        // Try to find target user
        Optional<User> oUser = userRepository.findByEmail(signUpDto.email());

        if (oUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        Integer createdUserId = GenerateUserId();

        // If we don't found it so we'll create a new user
        User user = new User(createdUserId, signUpDto.firstName(), signUpDto.secondName(), signUpDto.birthday(),
                signUpDto.email(),
                passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        UserBalance userBalance = new UserBalance(createdUserId, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        User savedUser = userRepository.save(user);
        userBalanceRepository.save(userBalance);
        return savedUser;
    }

}
