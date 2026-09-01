package com.axians.eshop.dto.response.category;

import com.axians.eshop.enums.OrderStatus;

public record OrderStatusCountResponse(

		OrderStatus status,

		Long totalOrders

) {

}
