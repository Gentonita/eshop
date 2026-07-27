package com.axians.eshop.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@OneToMany(mappedBy = "cart")
	private List<CartItem> cartItems = new ArrayList<>();
	
	
	public Cart() {
		
	}
	
	  public Cart(User user) {
	        this.user = user;
	        this.createdAt = LocalDateTime.now();
	        this.updatedAt = LocalDateTime.now();
	    }

	    public UUID getId() {
	        return id;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public LocalDateTime getUpdatedAt() {
	        return updatedAt;
	    }

	    public User getUser() {
	        return user;
	    }
	    
	    public List<CartItem> getCartItems() {
	        return cartItems;
	    }

	    public void updateTimestamp() {
	        this.updatedAt = LocalDateTime.now();
	    }
	}
