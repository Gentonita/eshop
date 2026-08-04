package com.axians.eshop.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.axians.eshop.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal totalPrice;
	
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	@Column(nullable = false)
	private LocalDateTime orderDate;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	public Order(BigDecimal totalPrice, OrderStatus status, User user) {
		this.totalPrice = totalPrice;
		this.status = status;
		this.user = user;
		this.orderDate = LocalDateTime.now();

	}
	
	public void addOrderItem(OrderItem orderItem){
	    orderItems.add(orderItem);
	    orderItem.setOrder(this);
	}
                            
}