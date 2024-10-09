package com.practice.commerce.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.commerce.api.dto.request.OrderRequest;
import com.practice.commerce.api.dto.response.OrderResponse;
import com.practice.commerce.common.dto.ResponseMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order API", description = "상품 주문 관련 API")
public class OrderController {
	@PostMapping("/create")
	@Operation(summary = "주문 생성", description = "주문을 생성합니다. - 회원 token 추가 예정")
	public ResponseMessage<OrderResponse> createOrder(
		@RequestBody @Parameter(description = "주문 요청 정보", required = true) OrderRequest request) {

		// Mock 응답 상품 목록 생성
		List<OrderResponse.OrderedProduct> orderedProducts = request.getProducts().stream()
			.map(product -> new OrderResponse.OrderedProduct(product.getProductId(), product.getQuantity(), "Sample Product Name"))
			.collect(Collectors.toList());

		OrderResponse response = new OrderResponse(1L, UUID.randomUUID().toString(), orderedProducts);
		return ResponseMessage.ok(response);
	}

	@GetMapping("/view/{orderId}")
	@Operation(summary = "주문 조회", description = "주문 정보를 조회합니다.- 회원 token 추가 예정 ")
	public ResponseMessage<OrderResponse> viewOrder(
		@PathVariable @Parameter(description = "주문 ID", example = "1", required = true) Long orderId) {

		List<OrderResponse.OrderedProduct> products = Arrays.asList(
			new OrderResponse.OrderedProduct(100L, 2, "샘플 상품 1"),
			new OrderResponse.OrderedProduct(200L, 1, "샘플 상품 2")
		);
		OrderResponse response = new OrderResponse(1L, UUID.randomUUID().toString(), products);
		return ResponseMessage.ok(response);
	}

	@GetMapping("/user-orders")
	@Operation(summary = "사용자 주문 목록 조회", description = "해당 사용자의 주문 목록을 조회합니다. - 회원 토큰 추가 예정")
	public ResponseMessage<OrderResponse> viewOrdersByUser() {
		List<OrderResponse.OrderedProduct> products = Arrays.asList(
			new OrderResponse.OrderedProduct(100L, 2, "샘플 상품 1"),
			new OrderResponse.OrderedProduct(200L, 1, "샘플 상품 2")
		);
		OrderResponse response = new OrderResponse(1L, UUID.randomUUID().toString(), products);
		return ResponseMessage.ok(response);
	}
}
