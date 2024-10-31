package com.practice.commerce.api.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.commerce.api.dto.response.BalanceResponse;
import com.practice.commerce.common.RedisKeyPrefix;
import com.practice.commerce.domain.balance.Balance;
import com.practice.commerce.domain.balance.BalanceTransaction;
import com.practice.commerce.domain.common.TransactionType;
import com.practice.commerce.infrastructure.redis.RedisManager;
import com.practice.commerce.infrastructure.repository.balance.BalanceRepository;
import com.practice.commerce.infrastructure.repository.balance.BalanceTransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceUseCase {

	private final BalanceRepository balanceRepository;
	private final BalanceTransactionRepository transactionRepository;
	private final RedisManager redisManager;

	@Transactional
	public BalanceResponse chargeBalance(Long userId, Long amount) {
		String balanceKey = RedisKeyPrefix.BALANCE_KEY.withSuffix(userId.toString());

		return redisManager.executeWithLock(balanceKey, () -> {
			Balance balance = balanceRepository.findByUserIdOrElseThrow(userId);
			balance.setBalance(balance.getBalance() + amount);

			BalanceTransaction transaction = BalanceTransaction.builder()
				.userId(userId)
				.transactionType(TransactionType.CHARGE)
				.amount(amount)
				.currentBalance(balance.getBalance())
				.build();

			transactionRepository.save(transaction);
			balanceRepository.save(balance);

			redisManager.setValue(balanceKey, balance.getBalance());

			return new BalanceResponse(userId, balance.getBalance());
		});
	}

	@Transactional(readOnly = true)
	public BalanceResponse checkBalance(Long userId) {
		String balanceKey = RedisKeyPrefix.BALANCE_KEY.withSuffix(userId.toString());

		Long cachedBalance = (long) redisManager.getValue(balanceKey);
		if (cachedBalance != null) {
			return new BalanceResponse(userId, cachedBalance);
		} else {
			Balance balance = balanceRepository.findByUserIdOrElseThrow(userId);
			redisManager.setValue(balanceKey, balance.getBalance());
			return new BalanceResponse(userId, balance.getBalance());
		}
	}
}
