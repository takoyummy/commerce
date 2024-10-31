package com.practice.commerce.infrastructure.repository.balance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.practice.commerce.common.exception.MemberNotFoundException;
import com.practice.commerce.common.exception.ProductNotFoundException;
import com.practice.commerce.domain.balance.Balance;

import jakarta.persistence.LockModeType;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	default Balance findByUserIdOrElseThrow(Long userId) {
		return findById(userId).orElseThrow(() -> new MemberNotFoundException("해당하는 ID가 없습니다.: " + userId));
	}

	Optional<Balance> findByUserId(Long userId);
}
