package com.practice.commerce.infrastructure.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.practice.commerce.common.exception.ProductNotFoundException;
import com.practice.commerce.domain.product.Product;

import jakarta.persistence.LockModeType;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	default Product findByIdOrThrow(Long productId) {
		return findById(productId).orElseThrow(() -> new ProductNotFoundException());
	}

	List<Product> findAll();
}
