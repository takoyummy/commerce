package com.practice.commerce.infrastructure.repository.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practice.commerce.domain.order.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
	List<OrderItem> findByOrderId(UUID orderId);

	@Query("SELECT oi FROM OrderItem oi " +
		"WHERE oi.createdAt >= :startDate " +
		"GROUP BY oi.productId " +
		"ORDER BY SUM(oi.quantity) DESC")
	List<OrderItem> findTopOrderItemsBySalesInLastDays(@Param("startDate") LocalDateTime startDate, Pageable pageable);
}
