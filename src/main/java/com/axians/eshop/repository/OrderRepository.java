package com.axians.eshop.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
