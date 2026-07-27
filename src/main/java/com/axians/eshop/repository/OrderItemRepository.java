package com.axians.eshop.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}
