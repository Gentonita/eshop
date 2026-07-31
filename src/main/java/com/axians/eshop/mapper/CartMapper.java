package com.axians.eshop.mapper;

import org.springframework.stereotype.Component;

import com.axians.eshop.dto.response.cart.CartResponse;
import com.axians.eshop.entity.Cart;

@Component
public class CartMapper {

	public CartResponse toResponse(Cart cart) {
		return new CartResponse(cart.getId(), cart.getUser().getId(), cart.getUser().getFirstName() + " " + cart.getUser().getLastName(), cart.getCreatedAt());
	}

}
