package com.axians.eshop.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository <Cart, UUID> {
	
	Optional<Cart> findByIdAndDeletedAtIsNull(UUID id);

	Optional<Cart> findByUserIdAndDeletedAtIsNull(UUID userId);

	boolean existsByUserId(UUID userId);

	List<Cart> findByDeletedAtIsNull();

}
