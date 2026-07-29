package com.axians.eshop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.product.CreateProductRequest;
import com.axians.eshop.dto.response.product.ProductResponse;
import com.axians.eshop.entity.Category;
import com.axians.eshop.entity.Product;
import com.axians.eshop.exception.CategoryNotFoundException;
import com.axians.eshop.exception.ProductAlreadyExistsException;
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
	            throw new ProductAlreadyExistsException(
	                    "Product with this name already exists");
	        }

	        Category category = categoryRepo
	                .findByIdAndDeletedAtIsNull(request.getCategoryId())
	                .orElseThrow(() ->
	                        new CategoryNotFoundException(
	                                "Category with id "
	                                        + request.getCategoryId()
	                                        + " not found!"));

	        Product product = productMapper.toEntity(request, category);

	        Product savedProduct = productRepo.save(product);

	        return productMapper.toResponse(savedProduct);
	    }
	  
	  public List<ProductResponse> getAllProducts(){
		  List<Product> products = productRepo.findByDeletedAtIsNull();
		  
		  return products.stream()
				  .map(productMapper::toResponse)
				  .collect(Collectors.toList());
	  }
	  
	  
	}
