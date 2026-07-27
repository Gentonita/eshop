package com.axians.eshop.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axians.eshop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
	boolean existsByEmailValue(String email);
	
	List<User> findByDeletedAtIsNull();

}

