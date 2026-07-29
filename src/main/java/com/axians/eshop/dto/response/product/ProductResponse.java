package com.axians.eshop.dto.response.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

	private UUID id;
	private String name;
	private String description;
	private BigDecimal price;
	private Integer stockQuantity;
	private Boolean isActive;
	private UUID categoryId;
	private String categoryName;
    private LocalDateTime createdAt;

}
