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

import com.axians.eshop.dto.request.product.CreateProductRequest;
import com.axians.eshop.dto.response.product.ProductResponse;
import com.axians.eshop.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest dto) {

		ProductResponse response = productService.createProduct(dto);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> response = productService.getAllProducts();

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProductResponse> getById(@PathVariable UUID id){
		ProductResponse response = productService.getById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
		productService.deleteProduct(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}

}
