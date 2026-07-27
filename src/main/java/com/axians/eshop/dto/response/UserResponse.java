package com.axians.eshop.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axians.eshop.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private Role role;
    private LocalDateTime createdAt;
}
