package com.practice.commerce.api.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.practice.commerce.api.dto.request.OrderRequest;
import com.practice.commerce.domain.balance.Balance;
import com.practice.commerce.domain.product.Product;
import com.practice.commerce.infrastructure.repository.balance.BalanceRepository;
import com.practice.commerce.infrastructure.repository.product.ProductRepository;

@SpringBootTest
public class OrderUseCaseTest {

	@Autowired
	private OrderUseCase orderUseCase;

	@Autowired
	private BalanceRepository balanceRepository;

	@Autowired
	private ProductRepository productRepository;

	private Long userId;
	private OrderRequest orderRequest;

	@BeforeEach
	public void setup() {
		userId = 1L;

		OrderRequest.OrderedProduct product1 = new OrderRequest.OrderedProduct(1L, 2);
		OrderRequest.OrderedProduct product2 = new OrderRequest.OrderedProduct(2L, 1);
		orderRequest = new OrderRequest(Arrays.asList(product1, product2));

		balanceRepository.findByUserId(userId).ifPresentOrElse(
			balance -> {
				balance.setBalance(5000L); // 충분한 잔액으로 설정
				balanceRepository.save(balance);
			},
			() -> balanceRepository.save(Balance.createBalance(userId, 5000L))
		);

		productRepository.findById(1L).ifPresentOrElse(
			product -> {
				product.setStock(10);
				productRepository.save(product);
			},
			() -> productRepository.save(Product.createProduct(1L, "Product 1", 1000L, 10))
		);

		productRepository.findById(2L).ifPresentOrElse(
			product -> {
				product.setStock(5);
				productRepository.save(product);
			},
			() -> productRepository.save(Product.createProduct(2L, "Product 2", 2000L, 5))
		);
	}

	@Test
	public void testConcurrentOrderCreation() throws InterruptedException {
		int threadCount = 10;
		CountDownLatch latch = new CountDownLatch(threadCount);
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executor.submit(() -> {
				try {
					assertDoesNotThrow(() -> orderUseCase.createOrder(userId, orderRequest));
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executor.shutdown();
	}

}