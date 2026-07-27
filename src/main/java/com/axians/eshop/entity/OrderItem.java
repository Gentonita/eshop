package com.axians.eshop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public OrderItem() {
	}

	public OrderItem(Integer quantity, BigDecimal price, Order order, Product product) {

	    this.quantity = quantity;
	    this.price = price;
	    this.order = order;
	    this.product = product;
	    this.createdAt = LocalDateTime.now();
	    this.updatedAt = LocalDateTime.now();
	}

	public UUID getId() {
		return id;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
	public Order getOrder() {
	    return order;
	}
	
	public Product getProduct() {
	    return product;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}