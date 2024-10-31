package com.practice.commerce.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.commerce.api.dto.request.OrderRequest;
import com.practice.commerce.api.dto.response.OrderResponse;
import com.practice.commerce.api.usecase.OrderUseCase;
import com.practice.commerce.common.dto.ResponseMessage;
import com.practice.commerce.common.security.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/order")
@Tag(name = "Order API", description = "상품 주문 관련 API")
@RequiredArgsConstructor
public class OrderController {

	private final OrderUseCase orderUseCase;

	@PostMapping
	@Operation(summary = "주문 생성", description = "주문을 생성합니다.",
		security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseMessage<OrderResponse> createOrder(@UserId Long userId,
		@RequestBody @Parameter(description = "주문 요청 정보", required = true) OrderRequest request) {

		OrderResponse response = orderUseCase.createOrder(userId, request);
		return ResponseMessage.ok(response);
	}

	@GetMapping("/{orderId}")
	@Operation(summary = "주문 조회", description = "주문 정보를 조회합니다.",
		security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseMessage<OrderResponse> getOrder(@UserId Long userId,
		@PathVariable @Parameter(description = "주문 ID", required = true) UUID orderId) {

		OrderResponse response = orderUseCase.getOrderById(userId, orderId);
		return ResponseMessage.ok(response);
	}

	@GetMapping("/user-orders")
	@Operation(summary = "사용자 주문 목록 조회", description = "해당 사용자의 주문 목록을 조회합니다.")
	public ResponseMessage<List<OrderResponse>> viewOrdersByUser(@UserId Long userId) {
		List<OrderResponse> responses = orderUseCase.getOrdersByUserId(userId);
		return ResponseMessage.ok(responses);
	}
}
