package com.axians.eshop.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axians.eshop.dto.request.user.CreateUserRequest;
import com.axians.eshop.dto.request.user.UpdateUserRequest;
import com.axians.eshop.dto.response.user.UserResponse;
import com.axians.eshop.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {

	    UserResponse response = userService.createUser(request);

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers(){
		List<UserResponse> response = userService.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("{id}")
	public UserResponse getById(@PathVariable UUID id) {
		return userService.getById(id);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,@Valid @RequestBody UpdateUserRequest updateUserRequest ){
		UserResponse response = userService.updateUser(updateUserRequest, id);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

	    userService.deleteUser(id);

	    return ResponseEntity.noContent().build();
	}

}
