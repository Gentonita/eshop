package com.axians.eshop.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.axians.eshop.dto.request.order.CreateOrderRequest;
import com.axians.eshop.dto.response.category.OrderStatusCountResponse;
import com.axians.eshop.dto.response.order.OrderResponse;
import com.axians.eshop.dto.response.order.TotalRevenueResponse;
import com.axians.eshop.dto.response.order.UserTotalSpentResponse;
import com.axians.eshop.dto.response.product.TopSellingProductResponse;
import com.axians.eshop.entity.Cart;
import com.axians.eshop.entity.CartItem;
import com.axians.eshop.entity.Order;
import com.axians.eshop.entity.OrderItem;
import com.axians.eshop.entity.Product;
import com.axians.eshop.entity.User;
import com.axians.eshop.enums.OrderStatus;

import com.axians.eshop.exception.NotEnoughStockException;
import com.axians.eshop.exception.NotFoundException;

import com.axians.eshop.mapper.OrderMapper;
import com.axians.eshop.repository.CartItemRepository;
import com.axians.eshop.repository.CartRepository;
import com.axians.eshop.repository.OrderItemRepository;
import com.axians.eshop.repository.OrderRepository;
import com.axians.eshop.repository.ProductRepository;
import com.axians.eshop.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	private final OrderRepository orderRepo;
	private final OrderItemRepository orderItemRepo;
	private final CartRepository cartRepo;
	private final CartItemRepository cartItemRepo;
	private final ProductRepository productRepo;
	private final UserRepository userRepo;
	private final OrderMapper orderMapper;

	public OrderService(OrderRepository orderRepo, OrderItemRepository orderItemRepo, CartRepository cartRepo,
			CartItemRepository cartItemRepo, ProductRepository productRepo, UserRepository userRepo,
			OrderMapper orderMapper) {

		this.orderRepo = orderRepo;
		this.orderItemRepo = orderItemRepo;
		this.cartRepo = cartRepo;
		this.cartItemRepo = cartItemRepo;
		this.productRepo = productRepo;
		this.userRepo = userRepo;
		this.orderMapper = orderMapper;
	}

	@Transactional
	public OrderResponse createOrder(CreateOrderRequest request) {

		Cart cart = cartRepo.findByIdAndDeletedAtIsNull(request.getCartId())
				.orElseThrow(() -> new NotFoundException("Cart with id " + request.getCartId() + " not found!"));

		List<CartItem> cartItems = cartItemRepo.findByCartIdAndDeletedAtIsNull(cart.getId());

		if (cartItems.isEmpty()) {
			throw new NotFoundException("Cart is empty!");
		}

		User user = cart.getUser();

		BigDecimal totalPrice = cartItems.stream()
				.map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		Order order = new Order(totalPrice, OrderStatus.PENDING, user);

		Order savedOrder = orderRepo.save(order);

		for (CartItem cartItem : cartItems) {

			Product product = cartItem.getProduct();

			if (product.getStockQuantity() < cartItem.getQuantity()) {
				throw new NotEnoughStockException("Not enough stock for product: " + product.getName());
			}

			OrderItem orderItem = new OrderItem(cartItem.getQuantity(), product.getPrice(), savedOrder, product);

			savedOrder.addOrderItem(orderItem);

			orderItemRepo.save(orderItem);

			product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());

			productRepo.save(product);

			cartItem.setDeletedAt(LocalDateTime.now());

			cartItemRepo.save(cartItem);

		}

		return orderMapper.toResponse(savedOrder);
	}

	public List<OrderResponse> getAllOrders() {

		List<Order> orders = orderRepo.findByDeletedAtIsNull();

		return orders.stream().map(orderMapper::toResponse).collect(Collectors.toList());

	}

	public OrderResponse getOrderById(UUID id) {

		Order order = orderRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Order with id " + id + " not found!"));

		return orderMapper.toResponse(order);

	}

	public List<OrderResponse> getOrdersByUser(UUID userId) {

		return orderRepo.findByUserIdAndDeletedAtIsNull(userId).stream().map(orderMapper::toResponse).toList();

	}

	public void deleteOrder(UUID id) {

		Order order = orderRepo.findByIdAndDeletedAtIsNull(id)
				.orElseThrow(() -> new NotFoundException("Order with id " + id + " not found!"));

		order.setDeletedAt(LocalDateTime.now());

		orderRepo.save(order);

	}

	public UserTotalSpentResponse getTotalSpent(UUID userId) {

		User user = userRepo.findByIdAndDeletedAtIsNull(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));

		BigDecimal totalSpent =
		        Optional.ofNullable(
		                orderRepo.getTotalSpentByUser(userId)
		        ).orElse(BigDecimal.ZERO);

		long totalOrders = orderRepo.countByUserIdAndDeletedAtIsNull(userId);

		return new UserTotalSpentResponse(user.getFirstName() + " " + user.getLastName(), user.getId(), totalSpent,
				totalOrders);
	}

	public OrderStatusCountResponse countOrdersByStatus(OrderStatus status) {

		Long totalOrders = orderRepo.countOrdersByStatus(status);

		return new OrderStatusCountResponse(status, totalOrders);
	}
	
	public List<TopSellingProductResponse> getTopSellingProducts() {

	    return orderItemRepo.getTopSellingProducts()
	            .stream()
	            .map(result -> new TopSellingProductResponse(
	                    (String) result[0],
	                    ((Number) result[1]).longValue()
	            ))
	            .toList();
	}
	
	public TotalRevenueResponse getTotalRevenue() {

	    BigDecimal totalRevenue =
	            Optional.ofNullable(
	                    orderRepo.getTotalRevenue()
	            ).orElse(BigDecimal.ZERO);

	    return new TotalRevenueResponse(
	            totalRevenue
	    );
	}

}