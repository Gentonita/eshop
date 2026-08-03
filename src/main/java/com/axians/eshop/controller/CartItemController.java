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

import com.axians.eshop.dto.request.cartitem.CreateCartItemRequest;
import com.axians.eshop.dto.request.cartitem.UpdateCartItemRequest;
import com.axians.eshop.dto.response.cartitem.CartItemResponse;
import com.axians.eshop.service.CartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("cart-items")
public class CartItemController {
	
	private final CartItemService cartItemService;
	
	public CartItemController(CartItemService cartItemService) {
		this.cartItemService = cartItemService;
	}
	
	@PostMapping
	public ResponseEntity<CartItemResponse> addCartItem(@RequestBody @Valid CreateCartItemRequest request){
		CartItemResponse response = cartItemService.addCartItem(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@GetMapping
	public ResponseEntity<List<CartItemResponse>> getAllCartItems(){
		
		List<CartItemResponse> response = cartItemService.getAllCartItems();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@GetMapping("cart/{cartId}")
	public ResponseEntity<List<CartItemResponse>> getAllCartItemsByCartId(@PathVariable UUID cartId){
		List<CartItemResponse> response = cartItemService.getCartItemsByCartId(cartId);
		
		return ResponseEntity.status(HttpStatus.OK).body(response); 
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CartItemResponse> getCartItemById(@PathVariable UUID id) {

	    CartItemResponse response = cartItemService.getCartItemById(id);

	    return ResponseEntity.ok(response);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<CartItemResponse> updateCartItem(
	        @PathVariable UUID id,
	        @RequestBody @Valid UpdateCartItemRequest request) {

	    CartItemResponse response = cartItemService.updateCartItem(id, request);

	    return ResponseEntity.ok(response);
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCartItem(@PathVariable UUID id) {

	    cartItemService.deleteCartItem(id);

	    return ResponseEntity.noContent().build();
	}
	

}
