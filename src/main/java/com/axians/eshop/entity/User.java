package com.axians.eshop.entity;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.axians.eshop.enums.Role;
import com.axians.eshop.valueobject.Email;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Embedded
	private Email email;

	@Column(nullable = false)
	private String password;

	private LocalDate birthday;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	private LocalDateTime deletedAt;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@OneToOne(mappedBy = "user")
	private Cart cart;
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders = new ArrayList<>();

	public User(String firstName, String lastName, Email email, String password, LocalDate birthday, Role role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthday = birthday;
		this.role = role;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	

	public void updateTimestamp() {
		this.updatedAt = LocalDateTime.now();
	}
}