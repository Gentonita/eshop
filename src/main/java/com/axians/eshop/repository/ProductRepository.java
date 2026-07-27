package com.axians.eshop.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

}
