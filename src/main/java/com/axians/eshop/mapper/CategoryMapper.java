package com.axians.eshop.mapper;

import org.springframework.stereotype.Component;

import com.axians.eshop.dto.request.category.CreateCategoryRequest;
import com.axians.eshop.dto.response.category.CategoryResponse;
import com.axians.eshop.entity.Category;

@Component
public class CategoryMapper {
	
	public Category toEntity(CreateCategoryRequest request) {
		return new Category(
				request.getName().trim(),
				request.getDescription()
				);
	}
	
	public CategoryResponse toResponse(Category category) {
		return new CategoryResponse(
				category.getId(),
				category.getName(),
				category.getDescription(),
				category.getCreatedAt()
				);
	}

}
