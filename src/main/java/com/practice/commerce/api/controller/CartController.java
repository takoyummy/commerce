package com.practice.commerce.api.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.commerce.api.dto.request.CartRequest;
import com.practice.commerce.api.dto.response.CartResponse;
import com.practice.commerce.common.dto.ResponseMessage;
import com.practice.commerce.common.security.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/cart")
@Tag(name = "Cart API", description = "장바구니 관련 API")
public class CartController {
	@PostMapping
	@Operation(summary = "장바구니에 상품 추가", description = "장바구니에 상품을 추가합니다. - 헤더에 멤버 토큰 추가 예정 ",
		security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseMessage<CartResponse> addItemToCart(@UserId Long userId,
		@RequestBody @Parameter(description = "장바구니에 추가할 상품 정보", required = true) CartRequest request) {
		CartResponse response = new CartResponse(1L, request.getProductId(), request.getQuantity());
		return ResponseMessage.ok(response);
	}

	@DeleteMapping("/{productId}")
	@Operation(summary = "장바구니에서 상품 삭제", description = "장바구니에서 특정 상품을 삭제합니다.  - 헤더에 멤버 토큰 추가 예정",
		security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseMessage<CartResponse> removeItemFromCart(@UserId Long userId,
		@PathVariable @Parameter(description = "상품 ID", example = "100", required = true) Long productId) {
		CartResponse response = new CartResponse(1L, productId, 0);
		return ResponseMessage.ok(response);
	}

	@GetMapping
	@Operation(summary = "장바구니 조회", description = "해당 사용자의 장바구니를 조회합니다. - 헤더에 멤버 토큰 추가 예정",
		security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseMessage<CartResponse> getCart(@UserId Long userId) {
		CartResponse response = new CartResponse(1L, 100L, 2);
		return ResponseMessage.ok(response);
	}
}
