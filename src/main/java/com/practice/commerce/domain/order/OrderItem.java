package com.practice.commerce.domain.order;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.practice.commerce.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(updatable = false, nullable = false, unique = true)
	private UUID orderItemId;

	@Column(nullable = false)
	private UUID orderId;

	@Column(nullable = false)
	private Long productId;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Long unitPrice;

	@Column(nullable = false)
	private String productName;

	@Builder
	public OrderItem(Long productId, Integer quantity, Long unitPrice, String productName) {
		this.productId = productId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.productName = productName;
	}
}