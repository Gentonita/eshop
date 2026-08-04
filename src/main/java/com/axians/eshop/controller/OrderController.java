package com.axians.eshop.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.axians.eshop.dto.request.order.CreateOrderRequest;
import com.axians.eshop.dto.response.order.OrderResponse;
import com.axians.eshop.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {

		return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<OrderResponse>> getAllOrders() {

		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@GetMapping("{id}")
	public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID id) {

		return ResponseEntity.ok(orderService.getOrderById(id));
	}

	@GetMapping("user/{userId}")
	public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable UUID userId) {

		return ResponseEntity.ok(orderService.getOrdersByUser(userId));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {

		orderService.deleteOrder(id);

		return ResponseEntity.noContent().build();
	}

}