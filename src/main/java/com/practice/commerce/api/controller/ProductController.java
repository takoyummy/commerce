package com.practice.commerce.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.commerce.api.dto.response.ProductResponse;
import com.practice.commerce.api.usecase.ProductUseCase;
import com.practice.commerce.common.dto.ResponseMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product API", description = "상품 관련 API")
@RequiredArgsConstructor
public class ProductController {

	private final ProductUseCase productUseCase;

	@GetMapping("/{productId}")
	@Operation(summary = "상품 조회", description = "특정 상품의 정보를 조회합니다.")
	public ResponseMessage<ProductResponse> getProduct(
		@PathVariable @Parameter(description = "상품 ID", example = "1", required = true) Long productId) {
		ProductResponse response = productUseCase.getProductById(productId);
		return ResponseMessage.ok(response);
	}

	@GetMapping("/list")
	@Operation(summary = "상품 목록 조회", description = "전체 상품 목록을 조회합니다.")
	public ResponseMessage<List<ProductResponse>> getProductList() {
		List<ProductResponse> productList = productUseCase.getAllProducts();
		return ResponseMessage.ok(productList);
	}


	@GetMapping("/popular")
	@Operation(summary = "인기 상품 목록 조회", description = "최근 3일간 가장 많이 팔린 인기 상품 목록을 조회합니다.")
	public ResponseMessage<List<ProductResponse>> getPopularProducts() {
		List<ProductResponse> popularProducts = productUseCase.getPopularProducts();
		return ResponseMessage.ok(popularProducts);
	}
}
