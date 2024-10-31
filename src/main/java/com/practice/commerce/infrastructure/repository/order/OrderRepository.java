package com.practice.commerce.infrastructure.repository.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.commerce.common.exception.OrderNotFoundException;
import com.practice.commerce.domain.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
	List<Order> findAllByUserId(Long userId);

	Optional<Order> findByUserIdAndOrderId(Long userId, UUID orderId);

	default Order findByUserIdAndOrderIdOrElseThrow(Long userId, UUID orderId) {
		return findByUserIdAndOrderId(userId, orderId)
			.orElseThrow(() -> new OrderNotFoundException("userId: " + userId + ", orderId: " + orderId));
	}

}
