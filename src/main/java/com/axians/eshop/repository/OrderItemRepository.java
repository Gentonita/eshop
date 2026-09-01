package com.axians.eshop.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

	@Query("""
		    SELECT oi.product.name,
		           SUM(oi.quantity)
		    FROM OrderItem oi
		    GROUP BY oi.product.name
		    ORDER BY SUM(oi.quantity) DESC
		    LIMIT 5
		""")
		List<Object[]> getTopSellingProducts();
}
