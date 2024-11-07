package com.practice.commerce.api.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.practice.commerce.api.dto.response.ProductResponse;
import com.practice.commerce.domain.order.OrderItem;
import com.practice.commerce.domain.product.Product;
import com.practice.commerce.infrastructure.repository.order.OrderItemRepository;
import com.practice.commerce.infrastructure.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductUseCase {

	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;

	@Cacheable(value = "productCache", key = "#productId")
	public ProductResponse getProductById(Long productId) {
		Product product = productRepository.findByIdOrThrow(productId);

		return ProductResponse.builder()
			.productId(product.getProductId())
			.name(product.getName())
			.price(product.getPrice())
			.stock(product.getStock())
			.build();
	}

	@Cacheable(value = "allProductsCache", cacheManager = "cacheManager")
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

	@Cacheable(value = "popularProductsCache", cacheManager = "cacheManager")
	public List<ProductResponse> getPopularProducts() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(3);

		List<OrderItem> topOrderItems = orderItemRepository.findTopOrderItemsBySalesInLastDays(startDate, PageRequest.of(0, 5));

		List<Long> topProductIds = topOrderItems.stream()
			.map(OrderItem::getProductId)
			.distinct()
			.collect(Collectors.toList());

		return productRepository.findAllById(topProductIds).stream()
			.map(product -> ProductResponse.builder()
				.productId(product.getProductId())
				.name(product.getName())
				.price(product.getPrice())
				.stock(product.getStock())
				.build())
			.collect(Collectors.toList());
	}
}
