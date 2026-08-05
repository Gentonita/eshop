package com.axians.eshop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.category.CreateCategoryRequest;
import com.axians.eshop.dto.request.category.UpdateCategoryRequest;
import com.axians.eshop.dto.response.category.CategoryResponse;
import com.axians.eshop.entity.Category;
import com.axians.eshop.exception.AlreadyExistsException;

import com.axians.eshop.exception.NotFoundException;
import com.axians.eshop.mapper.CategoryMapper;
import com.axians.eshop.repository.CategoryRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepo;
	private final CategoryMapper categoryMapper;

	public CategoryService(CategoryRepository categoryRepo, CategoryMapper categoryMapper) {
		this.categoryRepo = categoryRepo;
		this.categoryMapper = categoryMapper;
	}

	public CategoryResponse createCategory(CreateCategoryRequest request) {

		String name = request.getName().trim();

		if (categoryRepo.existsByNameIgnoreCaseAndDeletedAtIsNull(name)) {
			throw new AlreadyExistsException("Category with this name already exists");
		}

		Category category = categoryMapper.toEntity(request);

		Category savedCategory = categoryRepo.save(category);

		return categoryMapper.toResponse(savedCategory);
	}

	public List<CategoryResponse> getAllCategories() {

		List<Category> categories = categoryRepo.findByDeletedAtIsNull();

		return categories.stream().map(categoryMapper::toResponse).collect(Collectors.toList());
	}

	public CategoryResponse getById(UUID id) {

		Category category = categoryRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Category with id " + id + " not found!"));

		return categoryMapper.toResponse(category);
	}

	public CategoryResponse updateCategory(UpdateCategoryRequest updateRequest, UUID id) {

		String name = updateRequest.getName().trim();

		Category category = categoryRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Category with id " + id + " not found!"));

		if (!category.getName().equalsIgnoreCase(name) && categoryRepo.existsByNameIgnoreCaseAndDeletedAtIsNull(name)) {

			throw new AlreadyExistsException("Category with this name already exists");
		}

		category.setName(name);
		category.setDescription(updateRequest.getDescription());

		Category updatedCategory = categoryRepo.save(category);

		return categoryMapper.toResponse(updatedCategory);
	}

	public void deleteCategory(UUID id) {

		Category category = categoryRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Category with id " + id + " not found!"));

		category.setDeletedAt(LocalDateTime.now());

		categoryRepo.save(category);
	}

}