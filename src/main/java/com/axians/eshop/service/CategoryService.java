package com.axians.eshop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.category.CreateCategoryRequest;
import com.axians.eshop.dto.response.category.CategoryResponse;
import com.axians.eshop.entity.Category;
import com.axians.eshop.exception.CategoryAlreadyExistsException;
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
		if (categoryRepo.existsByNameIgnoreCaseAndDeletedAtIsNull(request.getName())) {
			throw new CategoryAlreadyExistsException("Category with this name already exists");
		}

		Category category = categoryMapper.toEntity(request);
		Category savedCategory = categoryRepo.save(category);

		return categoryMapper.toResponse(savedCategory);
	}
	
	public List<CategoryResponse> getAllCategories(){
		List<Category> categories = categoryRepo.findByDeletedAtIsNull();
		
		return categories.stream()
				.map(categoryMapper::toResponse)
				.collect(Collectors.toList());
	}

}
