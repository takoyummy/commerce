package com.practice.commerce.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.commerce.api.dto.response.ProductResponse;
import com.practice.commerce.common.dto.ResponseMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product API", description = "상품 관련 API")
public class ProductController {
	@GetMapping("/{productId}")
	@Operation(summary = "상품 조회", description = "특정 상품의 정보를 조회합니다.")
	public ResponseMessage<ProductResponse> getProduct(
		@PathVariable @Parameter(description = "상품 ID", example = "1", required = true) Long productId) {
		ProductResponse response = new ProductResponse(productId, "Sample Product", 100L, 10);
		return ResponseMessage.ok(response);
	}

	@GetMapping("/list")
	@Operation(summary = "상품 목록 조회", description = "전체 상품 목록을 조회합니다.")
	public ResponseMessage<List<ProductResponse>> getProductList() {
		List<ProductResponse> productList = Arrays.asList(
			new ProductResponse(1L, "상품 1", 100L, 10),
			new ProductResponse(2L, "상품 2", 150L, 5)
		);
		return ResponseMessage.ok(productList);
	}


	@GetMapping("/popular")
	@Operation(summary = "인기 상품 목록 조회", description = "최근 3일간 가장 많이 팔린 인기 상품 목록을 조회합니다.")
	public ResponseMessage<List<ProductResponse>> getPopularProducts() {
		List<ProductResponse> popularProducts = Arrays.asList(
			new ProductResponse(3L, "인기상품 1", 200L, 20),
			new ProductResponse(4L, "인기상품 2", 250L, 15),
			new ProductResponse(5L, "인기상품 3", 180L, 30),
			new ProductResponse(6L, "인기상품 4", 300L, 12),
			new ProductResponse(7L, "인기상품 5", 120L, 8)
		);
		return ResponseMessage.ok(popularProducts);
	}
}
