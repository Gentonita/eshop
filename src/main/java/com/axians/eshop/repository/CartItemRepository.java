package com.axians.eshop.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

	Optional<CartItem> findByIdAndDeletedAtIsNull(UUID id);

	List<CartItem> findByDeletedAtIsNull();

	Optional<CartItem> findByCartIdAndProductIdAndDeletedAtIsNull(UUID cartId, UUID productId);
	
	List<CartItem> findByCartIdAndDeletedAtIsNull(UUID cartId);

}