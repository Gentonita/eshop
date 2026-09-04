package com.axians.eshop.repository;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

	boolean existsByNameIgnoreCaseAndDeletedAtIsNull(String name);

	List<Product> findByDeletedAtIsNull();

	List<Product> findByCategory_IdAndDeletedAtIsNull(UUID id);
	
	
	List<Product> findByCategory_NameIgnoreCaseAndDeletedAtIsNull(String categoryName);
	
	List<Product> findByIsActiveTrueAndDeletedAtIsNull();
	
	List<Product> findByNameStartingWithIgnoreCaseAndDeletedAtIsNull(String name);
	
	Optional<Product> findByIdAndDeletedAtIsNull(UUID id);
	
	List<Product> findByPriceBetweenAndDeletedAtIsNullOrderByPriceAsc(
	        BigDecimal minPrice,
	        BigDecimal maxPrice
	);
	
	
	List<Product> findByDeletedAtIsNullOrderByPriceAsc();

	List<Product> findByDeletedAtIsNullOrderByPriceDesc();
	

	List<Product> findTop5ByDeletedAtIsNullOrderByPriceDesc();
	
	List<Product> findTop5ByDeletedAtIsNullOrderByPriceAsc();
	
	List<Product> findByStockQuantityLessThanAndDeletedAtIsNull(Integer quantity);
	
	List<Product> findByStockQuantityLessThanEqualAndDeletedAtIsNull(Integer quantity);
	
	Page<Product> findByDeletedAtIsNull(Pageable pageable);
	
	Page<Product> findByCategory_NameIgnoreCaseAndDeletedAtIsNull(
	        String categoryName,
	        Pageable pageable
	);
}
