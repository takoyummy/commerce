package com.practice.commerce.infrastructure.repository.balance;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.commerce.domain.balance.BalanceTransaction;

public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, UUID> {
	List<BalanceTransaction> findByUserId(Long userId);
}
