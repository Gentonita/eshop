package com.axians.eshop.dto.request.cartitem;


import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartItemRequest {
	
	@NotNull(message = "Cart Id is required")
	private UUID cartId;
	@NotNull(message = "Product Id is required")
	private UUID productId;
	
	@NotNull(message = "Quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;

}


