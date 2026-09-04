package com.axians.eshop.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.product.CreateProductRequest;
import com.axians.eshop.dto.request.product.UpdateProductRequest;
import com.axians.eshop.dto.request.product.UpdateStockRequest;
import com.axians.eshop.dto.response.product.ProductResponse;
import com.axians.eshop.entity.Category;
import com.axians.eshop.entity.Product;
import com.axians.eshop.exception.AlreadyExistsException;

import com.axians.eshop.exception.NotFoundException;

import com.axians.eshop.mapper.ProductMapper;
import com.axians.eshop.repository.CategoryRepository;
import com.axians.eshop.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepo;
	private final CategoryRepository categoryRepo;
	private final ProductMapper productMapper;

	public ProductService(ProductRepository productRepo, CategoryRepository categoryRepo, ProductMapper productMapper) {

		this.productRepo = productRepo;
		this.categoryRepo = categoryRepo;
		this.productMapper = productMapper;
	}

	public ProductResponse createProduct(CreateProductRequest request) {

		String name = request.getName().trim();

		if (productRepo.existsByNameIgnoreCaseAndDeletedAtIsNull(name)) {
			throw new AlreadyExistsException("Product with this name already exists");
		}

		Category category = categoryRepo.findByIdAndDeletedAtIsNull(request.getCategoryId()).orElseThrow(
				() -> new NotFoundException("Category with id " + request.getCategoryId() + " not found!"));

		Product product = productMapper.toEntity(request, request.getImageName(), category);

		Product savedProduct = productRepo.save(product);

		return productMapper.toResponse(savedProduct);
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepo.findByDeletedAtIsNull();

		return products.stream().map(productMapper::toResponse).collect(Collectors.toList());
	}

	public ProductResponse getById(UUID id) {
		Product product = productRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Product with id " + id + " not found!"));

		return productMapper.toResponse(product);
	}

	public ProductResponse updateProduct(UpdateProductRequest updateRequest, UUID id) {

		String name = updateRequest.getName().trim();

		Product product = productRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Product with id " + id + " not found!"));

		Category category = categoryRepo.findByIdAndDeletedAtIsNull(updateRequest.getCategoryId()).orElseThrow(
				() -> new NotFoundException("Category with id " + updateRequest.getCategoryId() + " not found!"));

		if (!product.getName().equalsIgnoreCase(name) && productRepo.existsByNameIgnoreCaseAndDeletedAtIsNull(name)) {

			throw new AlreadyExistsException("Product with this name already exists");
		}

		product.setName(name);
		product.setDescription(updateRequest.getDescription());
		product.setPrice(updateRequest.getPrice());
		product.setCategory(category);

		Product updatedProduct = productRepo.save(product);

		return productMapper.toResponse(updatedProduct);
	}

	public ProductResponse updateStock(UUID id, UpdateStockRequest request) {

		Product product = productRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Product with id " + id + " not found!"));

		product.setStockQuantity(request.getStockQuantity());

		return productMapper.toResponse(productRepo.save(product));
	}

	public void deleteProduct(UUID id) {
		Product product = productRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Product with id " + id + " not found!"));

		product.setDeletedAt(LocalDateTime.now());
		productRepo.save(product);

	}

	public List<ProductResponse> getProductsByCategory(UUID categoryId) {

		return productRepo.findByCategory_IdAndDeletedAtIsNull(categoryId).stream().map(productMapper::toResponse)
				.toList();
	}
	
	public List<ProductResponse> getProductsByCategoryName(String categoryName) {
	    return productRepo
	            .findByCategory_NameIgnoreCaseAndDeletedAtIsNull(categoryName)
	            .stream()
	            .map(productMapper::toResponse)
	            .toList();
	}

	public List<ProductResponse> getActiveProducts() {

		return productRepo.findByIsActiveTrueAndDeletedAtIsNull().stream().map(productMapper::toResponse).toList();
	}

	public List<ProductResponse> searchProducts(String name) {

		return productRepo.findByNameStartingWithIgnoreCaseAndDeletedAtIsNull(name).stream()
				.map(productMapper::toResponse).toList();
	}

	public List<ProductResponse> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {

		return productRepo.findByPriceBetweenAndDeletedAtIsNullOrderByPriceAsc(minPrice, maxPrice).stream()
				.map(productMapper::toResponse).toList();
	}

	public List<ProductResponse> getProductsByPriceAsc() {

		return productRepo.findByDeletedAtIsNullOrderByPriceAsc().stream().map(productMapper::toResponse).toList();
	}

	public List<ProductResponse> getProductsByPriceDesc() {

		return productRepo.findByDeletedAtIsNullOrderByPriceDesc().stream().map(productMapper::toResponse).toList();
	}

	public List<ProductResponse> getTop5MostExpensiveProducts() {

		return productRepo.findTop5ByDeletedAtIsNullOrderByPriceDesc().stream().map(productMapper::toResponse).toList();
	}

	public List<ProductResponse> getLowStockProducts(Integer quantity) {

		return productRepo.findByStockQuantityLessThanEqualAndDeletedAtIsNull(quantity).stream()
				.map(productMapper::toResponse).toList();
	}

	public Page<ProductResponse> getAllProducts(
	        int page,
	        int size,
	        String sortBy,
	        String direction,
	        String categoryName) {

	    Sort sort = direction.equalsIgnoreCase("desc")
	            ? Sort.by(sortBy).descending()
	            : Sort.by(sortBy).ascending();

	    Pageable pageable = PageRequest.of(page, size, sort);

	    if (categoryName != null && !categoryName.isBlank()) {
	        return productRepo
	                .findByCategory_NameIgnoreCaseAndDeletedAtIsNull(categoryName, pageable)
	                .map(productMapper::toResponse);
	    }

	    return productRepo
	            .findByDeletedAtIsNull(pageable)
	            .map(productMapper::toResponse);
	}

}
