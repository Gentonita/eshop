package com.axians.eshop.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String name;

	private String description;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "category")
	private List<Product> products = new ArrayList<>();
	
	public Category() {
	}

	public Category(String name, String description) {
	    this.name = name;
	    this.description = description;
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
    public List<Product> getProducts() {
        return products;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


}
