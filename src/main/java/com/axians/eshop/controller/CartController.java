package com.axians.eshop.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axians.eshop.dto.request.cart.CreateCartRequest;
import com.axians.eshop.dto.response.cart.CartResponse;
import com.axians.eshop.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("carts")
public class CartController {
	private final CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping
	public ResponseEntity<CartResponse> createCart(@RequestBody @Valid CreateCartRequest request) {
		CartResponse response = cartService.createCart(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<CartResponse>> getAllCarts() {
		List<CartResponse> carts = cartService.getAllCarts();

		return ResponseEntity.status(HttpStatus.OK).body(carts);

	}

	@GetMapping("{id}")
	public ResponseEntity<CartResponse> getCartById(@PathVariable UUID id) {
		CartResponse response = cartService.getCartById(id);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("user/{userId}")
	public ResponseEntity<CartResponse> getCartByUser(@PathVariable UUID userId) {
		CartResponse response = cartService.getCartByUser(userId);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCart(@PathVariable UUID id) {
		cartService.deleteCart(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

}
