package com.axians.eshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.LoginRequest;
import com.axians.eshop.dto.request.RegisterRequest;
import com.axians.eshop.dto.response.LoginResponse;
import com.axians.eshop.dto.response.RegisterResponse;
import com.axians.eshop.entity.User;
import com.axians.eshop.enums.Role;
import com.axians.eshop.exception.AlreadyExistsException;
import com.axians.eshop.exception.InvalidCredentialsException;
import com.axians.eshop.repository.UserRepository;
import com.axians.eshop.security.JwtService;
import com.axians.eshop.valueobject.Email;

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
        response.setUserId(user.getId());

        response.setFullName(
                user.getFirstName() + " " + user.getLastName()
        );
        
        response.setRole(user.getRole());

        return response;
    }
    
    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmailValue(request.email())) {
            throw new AlreadyExistsException("Email already exists");
        }

        User user = new User(
                request.firstName(),
                request.lastName(),
                new Email(request.email()),
                passwordEncoder.encode(request.password()),
                request.birthday(),
                Role.USER
        );

        userRepository.save(user);

        return new RegisterResponse(
                "User registered successfully"
        );
    }
    
}