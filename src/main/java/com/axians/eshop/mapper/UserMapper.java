package com.axians.eshop.mapper;

import org.springframework.stereotype.Component;

import com.axians.eshop.dto.request.user.CreateUserRequest;
import com.axians.eshop.dto.response.user.UserResponse;
import com.axians.eshop.entity.User;
import com.axians.eshop.enums.Role;
import com.axians.eshop.valueobject.Email;


@Component
public class UserMapper {
	
	public User toEntity(CreateUserRequest request, String hashedPassword) {
		return new User(
				request.getFirstName(),
				request.getLastName(),
				new Email(request.getEmail()),
				hashedPassword,
				request.getBirthday(),
				Role.USER
				
				);
		
	}
	public UserResponse toResponse(User user) {

        return new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail().getValue(),
            user.getBirthday(),
            user.getRole(),
            user.getCreatedAt()
        );
    }
}