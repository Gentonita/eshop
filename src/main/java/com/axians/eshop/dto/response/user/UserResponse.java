package com.axians.eshop.dto.response.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.axians.eshop.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private Role role;
    private LocalDateTime createdAt;
}
