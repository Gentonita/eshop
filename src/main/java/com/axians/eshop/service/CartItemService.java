package com.axians.eshop.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.cartitem.CreateCartItemRequest;
import com.axians.eshop.dto.request.cartitem.UpdateCartItemRequest;
import com.axians.eshop.dto.response.cartitem.CartDetailsResponse;
import com.axians.eshop.dto.response.cartitem.CartItemResponse;
import com.axians.eshop.entity.Cart;
import com.axians.eshop.entity.CartItem;
import com.axians.eshop.entity.Product;

import com.axians.eshop.exception.NotEnoughStockException;
import com.axians.eshop.exception.NotFoundException;

import com.axians.eshop.mapper.CartItemMapper;
import com.axians.eshop.repository.CartItemRepository;
import com.axians.eshop.repository.CartRepository;
import com.axians.eshop.repository.ProductRepository;

@Service
public class CartItemService {

	private final CartItemRepository cartItemRepo;
	private final CartRepository cartRepo;
	private final ProductRepository productRepo;
	private final CartItemMapper cartItemMapper;

	public CartItemService(CartItemRepository cartItemRepo, CartRepository cartRepo, ProductRepository productRepo,
			CartItemMapper cartItemMapper) {
		this.cartItemRepo = cartItemRepo;
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
		this.cartItemMapper = cartItemMapper;
	}

	public CartItemResponse addCartItem(CreateCartItemRequest request) {

		Cart cart = cartRepo.findByIdAndDeletedAtIsNull(request.getCartId())
				.orElseThrow(() -> new NotFoundException("Cart with id " + request.getCartId() + " not found!"));

		Product product = productRepo.findByIdAndDeletedAtIsNull(request.getProductId())
				.orElseThrow(() -> new NotFoundException("Product with id " + request.getProductId() + " not found!"));

		if (request.getQuantity() > product.getStockQuantity()) {
			throw new NotEnoughStockException("Only " + product.getStockQuantity() + " items are available in stock.");
		}

		Optional<CartItem> existingCartItem = cartItemRepo.findByCartIdAndProductIdAndDeletedAtIsNull(cart.getId(),
				product.getId());

		if (existingCartItem.isPresent()) {

			CartItem cartItem = existingCartItem.get();

			int newQuantity = cartItem.getQuantity() + request.getQuantity();

			if (newQuantity > product.getStockQuantity()) {
				throw new NotEnoughStockException(
						"Only " + product.getStockQuantity() + " items are available in stock.");
			}

			cartItem.setQuantity(newQuantity);

			return cartItemMapper.toResponse(cartItemRepo.save(cartItem));
		}

		CartItem newCartItem = new CartItem(request.getQuantity(), cart, product);

		return cartItemMapper.toResponse(cartItemRepo.save(newCartItem));
	}

	public List<CartItemResponse> getAllCartItems() {

		return cartItemRepo.findByDeletedAtIsNull().stream().map(cartItemMapper::toResponse)
				.collect(Collectors.toList());
	}

	public CartDetailsResponse getCartItemsByCartId(UUID cartId) {

		Cart cart = cartRepo.findByIdAndDeletedAtIsNull(cartId)
				.orElseThrow(() -> new NotFoundException("Cart with id " + cartId + " not found!"));

		List<CartItemResponse> items = cartItemRepo.findByCartIdAndDeletedAtIsNull(cartId).stream()
				.map(cartItemMapper::toResponse).collect(Collectors.toList());

		BigDecimal totalPrice = items.stream().map(CartItemResponse::getSubtotal).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		CartDetailsResponse response = new CartDetailsResponse();

		response.setCartId(cart.getId());
		response.setUserId(cart.getUser().getId());
		response.setUserName(cart.getUser().getFirstName() + " " + cart.getUser().getLastName());
		response.setTotalItems(items.size());
		response.setTotalPrice(totalPrice);
		response.setItems(items);

		return response;
	}

	public CartItemResponse getCartItemById(UUID id) {

		CartItem cartItem = cartItemRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Cart item with id " + id + " not found!"));

		return cartItemMapper.toResponse(cartItem);
	}

	public CartItemResponse updateCartItem(UUID id, UpdateCartItemRequest request) {

		CartItem cartItem = cartItemRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Cart item with id " + id + " not found!"));

		Product product = cartItem.getProduct();

		if (request.getQuantity() > product.getStockQuantity()) {
			throw new NotEnoughStockException("Only " + product.getStockQuantity() + " items are available in stock.");
		}

		cartItem.setQuantity(request.getQuantity());

		return cartItemMapper.toResponse(cartItemRepo.save(cartItem));
	}

	public void deleteCartItem(UUID id) {

		CartItem cartItem = cartItemRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Cart item with id " + id + " not found!"));

		cartItem.setDeletedAt(LocalDateTime.now());

		cartItemRepo.save(cartItem);
	}
}