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

import com.axians.eshop.dto.request.category.CreateCategoryRequest;
import com.axians.eshop.dto.request.category.UpdateCategoryRequest;
import com.axians.eshop.dto.response.category.CategoryResponse;
import com.axians.eshop.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("categories")
public class CategoryController {
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping
	public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
		CategoryResponse response = categoryService.createCategory(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getCategories() {

		List<CategoryResponse> response = categoryService.getAllCategories();

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("{id}")
	public ResponseEntity<CategoryResponse> getById(@PathVariable UUID id) {
		CategoryResponse response = categoryService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@PutMapping("{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable UUID id,
			@Valid @RequestBody UpdateCategoryRequest updateRequest) {
		CategoryResponse response = categoryService.updateCategory(updateRequest, id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
		categoryService.deleteCategory(id);

		return ResponseEntity.noContent().build();
	}

}
