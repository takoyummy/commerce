package com.practice.commerce.domain.product;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.practice.commerce.domain.common.BaseEntity;
import com.practice.commerce.domain.common.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stock_transaction")
@EqualsAndHashCode(callSuper = true)
public class StockTransaction extends BaseEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(updatable = false, nullable = false, unique = true)
	private UUID transactionId;

	@Column(nullable = false)
	private Long productId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType transactionType;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Integer currentStock;

	@Column
	private String description;
}
