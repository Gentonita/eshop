package com.axians.eshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

	@Column(nullable = false)
	private String name;

	private String description;

	@OneToMany(mappedBy = "category")
	private List<Product> products = new ArrayList<>();

	public Category(String name, String description) {
		this.name = name;
		this.description = description;

	}

}
