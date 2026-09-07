package com.axians.eshop.dto.response;

import java.util.UUID;

import com.axians.eshop.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String token;

    private UUID userId;

    private String fullName;
    
    private Role role;
}