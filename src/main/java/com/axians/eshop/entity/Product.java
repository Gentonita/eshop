package com.axians.eshop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String name;

	private String description;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer stockQuantity;

	@Column(nullable = false)
	private Boolean isActive = true;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private List<CartItem> cartItems = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<OrderItem> orderItems = new ArrayList<>();

	public Product() {
	}

	public Product(String name, String description, BigDecimal price, Integer stockQuantity, Category category) {

		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.category = category;
		this.isActive = true;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public List<OrderItem> getOrderItems() {
	    return orderItems;
	}
	
	public List<CartItem> getCartItems() {
	    return cartItems;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public Category getCategory() {
		return category;
	}

	public void updateTimestamp() {
		this.updatedAt = LocalDateTime.now();
	}
}