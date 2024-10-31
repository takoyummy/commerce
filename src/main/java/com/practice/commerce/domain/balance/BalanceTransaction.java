package com.practice.commerce.domain.balance;

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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "balance_transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class BalanceTransaction extends BaseEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(updatable = false, nullable = false, unique = true)
	private UUID transactionId;

	@Column(nullable = false)
	private Long userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType transactionType;

	@Column(nullable = false)
	private Long amount;

	@Column(nullable = false)
	private Long currentBalance;

	@Column
	private String externalTransactionId;

	@Column
	private UUID orderId;

	@Column
	private String description;

	@Builder // 특정 생성자에만 @Builder 적용
	public BalanceTransaction(Long userId, TransactionType transactionType, Long amount,
		Long currentBalance, String externalTransactionId) {
		this.userId = userId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.currentBalance = currentBalance;
		this.externalTransactionId = externalTransactionId;
	}

}
