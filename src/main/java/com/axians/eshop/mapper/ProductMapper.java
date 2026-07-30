package com.axians.eshop.mapper;

import org.springframework.stereotype.Component;

import com.axians.eshop.dto.request.product.CreateProductRequest;
import com.axians.eshop.dto.response.product.ProductResponse;
import com.axians.eshop.entity.Category;
import com.axians.eshop.entity.Product;
@Component
public class ProductMapper {

    public Product toEntity(CreateProductRequest request, Category category) {
        return new Product(
                request.getName().trim(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity(),
                category
        );
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getIsActive(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt()
        );
    }
}