package com.axians.eshop.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.Order;
import com.axians.eshop.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

	Optional<Order> findByIdAndDeletedAtIsNull(UUID id);

	List<Order> findByDeletedAtIsNull();

	List<Order> findByUserIdAndDeletedAtIsNull(UUID userId);

	@Query("""
			    SELECT SUM(o.totalPrice)
			    FROM Order o
			    WHERE o.user.id = :userId
			    AND o.deletedAt IS NULL
			""")
	BigDecimal getTotalSpentByUser(UUID userId);

	long countByUserIdAndDeletedAtIsNull(UUID userId);

	@Query("""
			    SELECT COUNT(o)
			    FROM Order o
			    WHERE o.status = :status
			    AND o.deletedAt IS NULL
			""")
	Long countOrdersByStatus(OrderStatus status);
	
	@Query("""
		    SELECT SUM(o.totalPrice)
		    FROM Order o
		    WHERE o.deletedAt IS NULL
		""")
		BigDecimal getTotalRevenue();
	
	
}
