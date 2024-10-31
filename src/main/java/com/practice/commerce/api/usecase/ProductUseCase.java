package com.practice.commerce.api.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.practice.commerce.api.dto.response.ProductResponse;
import com.practice.commerce.domain.product.Product;
import com.practice.commerce.infrastructure.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductUseCase {

	private final ProductRepository productRepository;

	/**
	 * 특정 상품 조회
	 *
	 * @param productId 조회할 상품 ID
	 * @return ProductResponse 상품 정보
	 */
	public ProductResponse getProductById(Long productId) {
		Product product = productRepository.findByIdOrThrow(productId);

		return ProductResponse.builder()
			.productId(product.getProductId())
			.name(product.getName())
			.price(product.getPrice())
			.stock(product.getStock())
			.build();
	}

	/**
	 * 전체 상품 목록 조회
	 *
	 * @return List<ProductResponse> 상품 목록
	 */
	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();

		return products.stream()
			.map(product -> ProductResponse.builder()
				.productId(product.getProductId())
				.name(product.getName())
				.price(product.getPrice())
				.stock(product.getStock())
				.build())
			.collect(Collectors.toList());
	}

	/**
	 * 인기 상품 목록 조회
	 *
	 * @return List<ProductResponse> 인기 상품 목록
	 */
	public List<ProductResponse> getPopularProducts() {
		// Mock data로 작성하였으나, 실제로는 최근 3일간 가장 많이 팔린 상품에 대한 통계 처리 필요
		return List.of(
			new ProductResponse(3L, "인기상품 1", 200L, 20),
			new ProductResponse(4L, "인기상품 2", 250L, 15),
			new ProductResponse(5L, "인기상품 3", 180L, 30),
			new ProductResponse(6L, "인기상품 4", 300L, 12),
			new ProductResponse(7L, "인기상품 5", 120L, 8)
		);
	}
}
