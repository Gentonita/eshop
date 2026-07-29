package com.axians.eshop.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
	
	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;
	
	
	private String description;

}