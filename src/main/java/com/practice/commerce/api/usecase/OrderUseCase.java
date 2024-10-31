package com.practice.commerce.api.usecase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.commerce.api.dto.request.OrderRequest;
import com.practice.commerce.api.dto.response.OrderResponse;
import com.practice.commerce.common.exception.InsufficientBalanceException;
import com.practice.commerce.common.exception.InventoryShortageException;
import com.practice.commerce.domain.balance.Balance;
import com.practice.commerce.domain.balance.BalanceTransaction;
import com.practice.commerce.domain.common.TransactionType;
import com.practice.commerce.domain.order.Order;
import com.practice.commerce.domain.order.OrderItem;
import com.practice.commerce.domain.order.OrderStatus;
import com.practice.commerce.domain.product.Product;
import com.practice.commerce.infrastructure.adapter.ExternalDataPlatformAdapter;
import com.practice.commerce.infrastructure.redis.RedisManager;
import com.practice.commerce.infrastructure.repository.balance.BalanceRepository;
import com.practice.commerce.infrastructure.repository.balance.BalanceTransactionRepository;
import com.practice.commerce.infrastructure.repository.order.OrderItemRepository;
import com.practice.commerce.infrastructure.repository.order.OrderRepository;
import com.practice.commerce.infrastructure.repository.product.ProductRepository;
import com.practice.commerce.common.RedisKeyPrefix;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderUseCase {
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final BalanceRepository balanceRepository;
	private final BalanceTransactionRepository balanceTransactionRepository;
	private final ProductRepository productRepository;
	private final ExternalDataPlatformAdapter externalDataPlatformAdapter;
	private final RedisManager redisManager;

	@Transactional
	public OrderResponse createOrder(Long userId, OrderRequest orderRequest) {
		List<OrderItem> orderItems = createOrderItems(orderRequest);
		long totalPrice = calculateTotalPrice(orderItems);

		return redisManager.executeWithLock(RedisKeyPrefix.BALANCE_KEY.withSuffix(userId.toString()), () -> {
			deductUserBalance(userId, totalPrice);
			Order savedOrder = saveOrder(userId, totalPrice, orderItems);
			recordBalanceTransaction(userId, totalPrice, TransactionType.DEDUCT, savedOrder.getOrderId());
			sendOrderToExternalPlatform(savedOrder);
			return createOrderResponse(savedOrder, orderItems);
		});
	}

	private List<OrderItem> createOrderItems(OrderRequest orderRequest) {
		return orderRequest.getProducts().stream()
			.map(this::createOrderItem)
			.collect(Collectors.toList());
	}

	private OrderItem createOrderItem(OrderRequest.OrderedProduct productRequest) {
		Product product = productRepository.findByIdOrThrow(productRequest.getProductId());
		String stockKey = RedisKeyPrefix.STOCK_KEY.withSuffix(String.valueOf(product.getProductId()));

		return redisManager.executeWithLock(stockKey, () -> {
			Integer currentStock = (Integer) redisManager.getValue(stockKey);
			if (currentStock == null || currentStock < productRequest.getQuantity()) {
				throw new InventoryShortageException();
			}

			redisManager.incrementValue(stockKey, -productRequest.getQuantity());
			product.reduceStock(productRequest.getQuantity());
			productRepository.save(product);

			return OrderItem.builder()
				.productId(product.getProductId())
				.quantity(productRequest.getQuantity())
				.unitPrice(product.getPrice())
				.productName(product.getName())
				.build();
		});
	}

	private long calculateTotalPrice(List<OrderItem> orderItems) {
		return orderItems.stream()
			.mapToLong(item -> item.getQuantity() * item.getUnitPrice())
			.sum();
	}

	private void deductUserBalance(Long userId, long totalPrice) {
		String balanceKey = RedisKeyPrefix.BALANCE_KEY.withSuffix(userId.toString());

		Long currentBalance = (Long) redisManager.getValue(balanceKey);
		if (currentBalance == null || currentBalance < totalPrice) {
			throw new InsufficientBalanceException();
		}

		redisManager.incrementValue(balanceKey, -totalPrice);

		Balance userBalance = balanceRepository.findByUserIdOrElseThrow(userId);
		userBalance.deductBalance(totalPrice);
		balanceRepository.save(userBalance);
	}

	private void recordBalanceTransaction(Long userId, long amount, TransactionType transactionType, UUID orderId) {
		Balance balance = balanceRepository.findByUserIdOrElseThrow(userId);
		BalanceTransaction transaction = BalanceTransaction.builder()
			.userId(userId)
			.transactionType(transactionType)
			.amount(amount)
			.currentBalance(balance.getBalance())
			.externalTransactionId(orderId.toString())
			.build();
		balanceTransactionRepository.save(transaction);
	}

	private Order saveOrder(Long userId, long totalPrice, List<OrderItem> orderItems) {
		Order order = new Order(userId, totalPrice, OrderStatus.PENDING);
		orderRepository.save(order);

		orderItems.forEach(item -> {
			item.setOrderId(order.getOrderId());
			orderItemRepository.save(item);
		});

		order.setOrderStatus(OrderStatus.COMPLETED);
		orderRepository.save(order);

		return order;
	}

	private void sendOrderToExternalPlatform(Order order) {
		externalDataPlatformAdapter.sendOrderData(order);
	}

	private OrderResponse createOrderResponse(Order order, List<OrderItem> orderItems) {
		List<OrderResponse.OrderedProduct> orderedProducts = orderItems.stream()
			.map(item -> new OrderResponse.OrderedProduct(
				item.getProductId(),
				item.getQuantity(),
				item.getProductName()))
			.collect(Collectors.toList());

		return new OrderResponse(order.getUserId(), order.getOrderId().toString(), orderedProducts);
	}

	@Transactional(readOnly = true)
	public OrderResponse getOrderById(Long userId, UUID orderId) {
		Order order = orderRepository.findByUserIdAndOrderIdOrElseThrow(userId, orderId);
		List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());

		List<OrderResponse.OrderedProduct> orderedProducts = orderItems.stream()
			.map(this::mapToOrderedProduct)
			.collect(Collectors.toList());

		return new OrderResponse(order.getUserId(), order.getOrderId().toString(), orderedProducts);
	}

	@Transactional(readOnly = true)
	public List<OrderResponse> getOrdersByUserId(Long userId) {
		List<Order> orders = orderRepository.findAllByUserId(userId);

		return orders.stream()
			.map(order -> {
				List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getOrderId());

				List<OrderResponse.OrderedProduct> orderedProducts = orderItems.stream()
					.map(this::mapToOrderedProduct)
					.collect(Collectors.toList());

				return new OrderResponse(order.getUserId(), order.getOrderId().toString(), orderedProducts);
			})
			.collect(Collectors.toList());
	}

	private OrderResponse.OrderedProduct mapToOrderedProduct(OrderItem item) {
		return new OrderResponse.OrderedProduct(
			item.getProductId(),
			item.getQuantity(),
			item.getProductName()
		);
	}

}
