package com.axians.eshop.dto.response.order;

import java.math.BigDecimal;
import java.util.UUID;

public record UserTotalSpentResponse(
        String fullName,
        UUID id,
        BigDecimal totalSpent,
        Long totalOrders
) {
}