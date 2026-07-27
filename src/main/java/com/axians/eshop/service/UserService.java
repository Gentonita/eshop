package com.axians.eshop.service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.CreateUserRequest;
import com.axians.eshop.dto.request.UpdateUserRequest;
import com.axians.eshop.dto.response.UserResponse;
import com.axians.eshop.entity.User;
import com.axians.eshop.exception.EmailAlreadyExistsException;
import com.axians.eshop.exception.UserNotFoundException;
import com.axians.eshop.mapper.UserMapper;
import com.axians.eshop.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepo,UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	public UserResponse createUser(CreateUserRequest request) {

	    if (userRepo.existsByEmailValue(request.getEmail())) {
	    	  throw new EmailAlreadyExistsException("User with this email already exists");
	    }
	    
	    String hashedPassword = passwordEncoder.encode(request.getPassword());

	    User user = userMapper.toEntity(request, hashedPassword);

	    User savedUser = userRepo.save(user);

	    return userMapper.toResponse(savedUser);
	}
	
	public List<UserResponse> getAllUsers() {

	    List<User> users = userRepo.findByDeletedAtIsNull();

	    return users.stream()
	            .map(userMapper::toResponse)
	            .collect(Collectors.toList());
	}
	
	public UserResponse getById(UUID id) {

	    User user = userRepo.findById(id)
	            .orElseThrow(() -> 
	                new UserNotFoundException("User with id " + id + " not found!")
	            );

	    return userMapper.toResponse(user);
	}
	
	public UserResponse updateUser(UpdateUserRequest updateUserRequest,UUID id) {
		User user = userRepo.findById(id) .orElseThrow(() -> 
        new UserNotFoundException("User with id " + id + " not found!"));
		
		user.setFirstName(updateUserRequest.getFirstName());
		user.setLastName(updateUserRequest.getLastName());
		user.setBirthday(updateUserRequest.getBirthday());
		user.updateTimestamp();
		
		User updatedUser = userRepo.save(user);
		
		return userMapper.toResponse(updatedUser);
		
	}
		
		public void deleteUser(UUID id) {
			User user = userRepo.findById(id).orElseThrow(() -> 
	        new UserNotFoundException("User with id " + id + " not found!"));
			
			user.setDeletedAt(LocalDateTime.now());
			user.updateTimestamp();
			
			userRepo.save(user);
		}
		
		
	

		
	
}
