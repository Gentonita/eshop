package com.axians.eshop.dto.response.product;

public record TopSellingProductResponse(
        String productName,
        Long totalSold
) {
}