package com.axians.eshop.dto.response.category;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

	private UUID id;
	private String name;
	private String description;
	private LocalDateTime createdAt;

}
