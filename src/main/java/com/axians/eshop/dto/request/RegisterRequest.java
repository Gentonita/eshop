package com.axians.eshop.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(

	    @NotBlank(message = "First name is required")
	    String firstName,

	    @NotBlank(message = "Last name is required")
	    String lastName,

	    @Email(message = "Invalid email format")
	    @NotBlank(message = "Email is required")
	    String email,

	    @Pattern(
	    	    regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
	    	    message = "Password must contain at least 8 characters, one uppercase letter, one number and one special character"
	    	)
	    @NotBlank(message = "Password is required")
	    String password,

	    @NotNull(message = "Birthday is required")
	    LocalDate birthday

	) {}