package com.axians.eshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.LoginRequest;
import com.axians.eshop.dto.response.LoginResponse;
import com.axians.eshop.entity.User;
import com.axians.eshop.exception.InvalidCredentialsException;
import com.axians.eshop.repository.UserRepository;
import com.axians.eshop.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmailValueAndDeletedAtIsNull(request.getEmail())
                .orElseThrow(() ->   new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

        	 throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail().getValue());

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return response;
        
        
    }
}