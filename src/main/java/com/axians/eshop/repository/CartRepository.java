package com.axians.eshop.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository <Cart, UUID> {

}
