package com.axians.eshop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.cartitem.CreateCartItemRequest;
import com.axians.eshop.dto.request.cartitem.UpdateCartItemRequest;
import com.axians.eshop.dto.response.cartitem.CartItemResponse;
import com.axians.eshop.entity.Cart;
import com.axians.eshop.entity.CartItem;
import com.axians.eshop.entity.Product;
import com.axians.eshop.exception.CartItemNotFoundException;
import com.axians.eshop.exception.CartNotFoundException;
import com.axians.eshop.exception.NotEnoughStockException;
import com.axians.eshop.exception.ProductNotFoundException;
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
	
	
	public CartItemService(CartItemRepository cartItemRepo,CartRepository cartRepo, ProductRepository productRepo, CartItemMapper cartItemMapper) {
		this.cartItemRepo = cartItemRepo;
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
		this.cartItemMapper = cartItemMapper;
	}
	
	public CartItemResponse addCartItem(CreateCartItemRequest request) {
		Cart cart = cartRepo.findByIdAndDeletedAtIsNull(request.getCartId()).orElseThrow(() -> new CartNotFoundException ("Cart with id " + request.getCartId() + " not found!"));
		
		Product product = productRepo.findByIdAndDeletedAtIsNull(request.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product with id " + request.getProductId() + " not found!"));
		
		if(request.getQuantity() > product.getStockQuantity()) {
			throw new NotEnoughStockException("Not enough stock available for this product");
		}
		
		Optional<CartItem> existingCartItem = cartItemRepo.findByCartIdAndProductIdAndDeletedAtIsNull(
		        cart.getId(),
		        product.getId()
		);
		
		if (existingCartItem.isPresent()) {

			CartItem cartItem = existingCartItem.get();

			int newQuantity = cartItem.getQuantity() + request.getQuantity();

			if (newQuantity > product.getStockQuantity()) {
				throw new NotEnoughStockException("Not enough stock available for this product");
			}

			cartItem.setQuantity(newQuantity);

			CartItem savedCartItem = cartItemRepo.save(cartItem);

			return cartItemMapper.toResponse(savedCartItem);
		}else {
			CartItem  newCartItem = new CartItem( 
					request.getQuantity(),
					cart,
					product
					);
			CartItem savedCartItem = cartItemRepo.save(newCartItem);

			return cartItemMapper.toResponse(savedCartItem);
		}
	}
	
	public List<CartItemResponse> getAllCartItems(){
		List<CartItem> cartItems = cartItemRepo.findByDeletedAtIsNull();
		
		return cartItems.stream()
				.map(cartItemMapper::toResponse)
				.collect(Collectors.toList());
	}
	
	public List<CartItemResponse> getCartItemsByCartId(UUID cartId){
		List<CartItem> cartItems  = cartItemRepo.findByCartIdAndDeletedAtIsNull(cartId);
		
		return cartItems .stream()
				.map(cartItemMapper::toResponse)
				.collect(Collectors.toList());
	}
	
	public CartItemResponse getCartItemById(UUID id) {

	    CartItem cartItem = cartItemRepo.findByIdAndDeletedAtIsNull(id)
	            .orElseThrow(() ->
	                    new CartItemNotFoundException(
	                            "Cart item with id " + id + " not found!"
	                    ));

	    return cartItemMapper.toResponse(cartItem);
	}
	
	public CartItemResponse updateCartItem(UUID id, UpdateCartItemRequest request) {

	    CartItem cartItem = cartItemRepo.findByIdAndDeletedAtIsNull(id)
	            .orElseThrow(() ->
	                    new CartItemNotFoundException(
	                            "Cart item with id " + id + " not found!"
	                    ));

	    Product product = cartItem.getProduct();

	    if (request.getQuantity() > product.getStockQuantity()) {
	        throw new NotEnoughStockException("Not enough stock available for this product");
	    }

	    cartItem.setQuantity(request.getQuantity());

	    CartItem updatedCartItem = cartItemRepo.save(cartItem);

	    return cartItemMapper.toResponse(updatedCartItem);
	}
	
	public void deleteCartItem(UUID id) {

	    CartItem cartItem = cartItemRepo.findByIdAndDeletedAtIsNull(id)
	            .orElseThrow(() ->
	                    new CartItemNotFoundException(
	                            "Cart item with id " + id + " not found!"
	                    ));

	    cartItem.setDeletedAt(LocalDateTime.now());

	    cartItemRepo.save(cartItem);
	}
}
