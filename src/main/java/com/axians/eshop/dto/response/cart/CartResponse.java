package com.axians.eshop.dto.response.cart;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
	private UUID id;

	private UUID userId;
	
	private String fullName;

	private LocalDateTime createdAt;

}
