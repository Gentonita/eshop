package com.axians.eshop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.cart.CreateCartRequest;
import com.axians.eshop.dto.response.cart.CartResponse;
import com.axians.eshop.entity.Cart;
import com.axians.eshop.entity.User;
import com.axians.eshop.exception.AlreadyExistsException;

import com.axians.eshop.exception.NotFoundException;

import com.axians.eshop.mapper.CartMapper;
import com.axians.eshop.repository.CartRepository;
import com.axians.eshop.repository.UserRepository;

@Service
public class CartService {

	private final CartRepository cartRepo;
	private final UserRepository userRepo;
	private final CartMapper cartMapper;

	public CartService(CartRepository cartRepo, UserRepository userRepo, CartMapper cartMapper) {
		this.cartRepo = cartRepo;
		this.userRepo = userRepo;
		this.cartMapper = cartMapper;
	}

	public CartResponse createCart(CreateCartRequest request) {

		User user = userRepo.findByIdAndDeletedAtIsNull(request.getUserId())
				.orElseThrow(() -> new NotFoundException("User with id " + request.getUserId() + " not found!"));

		if (cartRepo.existsByUserId(user.getId())) {
			throw new AlreadyExistsException("Cart already exists for this user.");
		}

		Cart cart = new Cart();
		cart.setUser(user);

		Cart savedCart = cartRepo.save(cart);

		return cartMapper.toResponse(savedCart);
	}

	public List<CartResponse> getAllCarts() {
		List<Cart> carts = cartRepo.findByDeletedAtIsNull();

		return carts.stream().map(cartMapper::toResponse).collect(Collectors.toList());
	}

	public CartResponse getCartById(UUID id) {
		Cart cart = cartRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Cart with id " + id + " not found!"));

		return cartMapper.toResponse(cart);
	}

	public CartResponse getCartByUser(UUID userId) {
		User user = userRepo.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new NotFoundException("User with id " + userId + " not found!"));

		Cart userCart = cartRepo.findByUserIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new NotFoundException("User with id " + userId + " does not have a cart."));

		return cartMapper.toResponse(userCart);

	}

}
