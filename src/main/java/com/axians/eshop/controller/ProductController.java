package com.axians.eshop.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axians.eshop.dto.request.product.CreateProductRequest;
import com.axians.eshop.dto.request.product.UpdateProductRequest;
import com.axians.eshop.dto.request.product.UpdateStockRequest;
import com.axians.eshop.dto.response.product.ProductResponse;
import com.axians.eshop.entity.Product;
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

	@GetMapping("/all")
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> response = productService.getAllProducts();

		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@GetMapping("{id}")
	public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) {
		ProductResponse response = productService.getById(id);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/category/{categoryName}")
	ResponseEntity<List<ProductResponse>> getByName(@PathVariable String categoryName) {
		List<ProductResponse> response = productService.getProductsByCategoryName(categoryName);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
 
	@PutMapping("{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id,
			@RequestBody @Valid UpdateProductRequest request) {

		ProductResponse response = productService.updateProduct(request, id);

		return ResponseEntity.ok(response);
	}
	
	@PutMapping("stock/{id}")
	public ResponseEntity<ProductResponse> updateStock(
	        @PathVariable UUID id,
	        @RequestBody @Valid UpdateStockRequest request) {

	    ProductResponse response = productService.updateStock(id, request);

	    return ResponseEntity.ok(response);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
		productService.deleteProduct(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
	@GetMapping("/category/id/{id}")
	public ResponseEntity<List<ProductResponse>> getProductsByCategory(
	        @PathVariable UUID id) {

	    List<ProductResponse> response =
	            productService.getProductsByCategory(id);

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/active")
	public ResponseEntity<List<ProductResponse>> getActiveProducts() {

	    List<ProductResponse> response =
	            productService.getActiveProducts();

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<ProductResponse>> searchProducts(@PathVariable String name) {
		  List<ProductResponse> response =
		            productService.searchProducts(name);
		
		  return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/price-range")
	public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(
	        @RequestParam BigDecimal minPrice,
	        @RequestParam BigDecimal maxPrice) {

	    return ResponseEntity.ok(
	            productService.getProductsByPriceRange(minPrice, maxPrice)
	    );
	}
	

	
	@GetMapping("/top-expensive")
	public ResponseEntity<List<ProductResponse>> getTop5MostExpensiveProducts() {

	    return ResponseEntity.ok(
	            productService.getTop5MostExpensiveProducts()
	    );
	}
	
	@GetMapping("/low-stock/{quantity}")
	public ResponseEntity<List<ProductResponse>> getLowStockProducts(
	        @PathVariable Integer quantity) {

	    return ResponseEntity.ok(
	            productService.getLowStockProducts(quantity)
	    );
	}
	
	@GetMapping
	public ResponseEntity<Page<ProductResponse>> getAllProducts(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "15") int size,
	        @RequestParam(defaultValue = "name") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction,
	        @RequestParam(required = false) String categoryName) {

	    return ResponseEntity.ok(
	            productService.getAllProducts(
	                    page,
	                    size,
	                    sortBy,
	                    direction,
	                    categoryName
	            )
	    );
	}

	
}
