package com.axians.eshop.dto.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.axians.eshop.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponse {

    private UUID orderId;
    
    private UUID userId;

    private String customerName;
    

    private OrderStatus status;

    private LocalDateTime orderDate;

    private Integer totalItems;

    private BigDecimal totalPrice;

    private List<OrderItemResponse> items;

}