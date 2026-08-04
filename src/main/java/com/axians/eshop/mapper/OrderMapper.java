package com.axians.eshop.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axians.eshop.dto.response.order.OrderItemResponse;
import com.axians.eshop.dto.response.order.OrderResponse;
import com.axians.eshop.entity.Order;
import com.axians.eshop.entity.OrderItem;
@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .toList();


        Integer totalItems = order.getOrderItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();


        String customerName = order.getUser().getFirstName()
                + " "
                + order.getUser().getLastName();


        return new OrderResponse(
                order.getId(),
                order.getUser().getId(), 
                customerName,
                order.getStatus(),
                order.getOrderDate(),
                totalItems,
                order.getTotalPrice(),
                items
        );
    }
}