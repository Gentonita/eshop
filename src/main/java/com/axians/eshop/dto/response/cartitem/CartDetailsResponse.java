package com.axians.eshop.dto.response.cartitem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsResponse {

    private UUID cartId;
    
    private String userName;

    private Integer totalItems;

    private BigDecimal totalPrice;

    private List<CartItemResponse> items;

}