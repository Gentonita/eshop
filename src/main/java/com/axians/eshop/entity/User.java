package com.axians.eshop.entity;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import com.axians.eshop.enums.Role;
import com.axians.eshop.valueobject.Email;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
public class User extends BaseEntity {

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
	}

}