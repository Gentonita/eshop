package com.axians.eshop.mapper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.axians.eshop.dto.request.cartitem.CreateCartItemRequest;
import com.axians.eshop.dto.response.cartitem.CartItemResponse;
import com.axians.eshop.entity.Cart;
import com.axians.eshop.entity.CartItem;
import com.axians.eshop.entity.Product;

@Component
public class CartItemMapper {

	public CartItem toEntity(CreateCartItemRequest request, Cart cart, Product product) {
		return new CartItem(
				request.getQuantity(),
				cart,
				product
		);
	}

	public CartItemResponse toResponse(CartItem cartItem) {

		BigDecimal unitPrice = cartItem.getProduct().getPrice();

		BigDecimal subtotal = unitPrice.multiply(
				BigDecimal.valueOf(cartItem.getQuantity())
		);

		return new CartItemResponse(
				cartItem.getId(),
				cartItem.getProduct().getId(),
				cartItem.getProduct().getName(),
				cartItem.getQuantity(),
				unitPrice,
				subtotal
		);
	}

}