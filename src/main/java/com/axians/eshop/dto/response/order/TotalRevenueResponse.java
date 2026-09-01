package com.axians.eshop.dto.response.order;

import java.math.BigDecimal;

public record TotalRevenueResponse(
        BigDecimal totalRevenue
) {
}
