package com.practice.commerce.domain.balance;

import com.practice.commerce.common.exception.InsufficientBalanceException;
import com.practice.commerce.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "balance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Balance extends BaseEntity {
	@Id
	private Long userId;

	@Column(nullable = false)
	private Long balance;

	public void deductBalance(long amount) {
		if (this.balance < amount) {
			throw new InsufficientBalanceException();
		}
		this.balance = amount;
	}

	public static Balance createBalance(Long userId, Long balanceAmount) {
		Balance balance = new Balance();
		balance.userId = userId;
		balance.balance = balanceAmount;
		return balance;
	}
}
