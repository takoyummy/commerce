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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart API", description = "장바구니 관련 API")
public class CartController {
	@PostMapping("/add")
	@Operation(summary = "장바구니에 상품 추가", description = "장바구니에 상품을 추가합니다. - 헤더에 멤버 토큰 추가 예정 ")
	public ResponseMessage<CartResponse> addItemToCart(
		@RequestBody @Parameter(description = "장바구니에 추가할 상품 정보", required = true) CartRequest request) {
		CartResponse response = new CartResponse(1L, request.getProductId(), request.getQuantity());
		return ResponseMessage.ok(response);
	}

	@DeleteMapping("/remove/{productId}")
	@Operation(summary = "장바구니에서 상품 삭제", description = "장바구니에서 특정 상품을 삭제합니다.  - 헤더에 멤버 토큰 추가 예정")
	public ResponseMessage<CartResponse> removeItemFromCart(
		@PathVariable @Parameter(description = "상품 ID", example = "100", required = true) Long productId) {
		CartResponse response = new CartResponse(1L, productId, 0);
		return ResponseMessage.ok(response);
	}

	@GetMapping
	@Operation(summary = "장바구니 조회", description = "해당 사용자의 장바구니를 조회합니다. - 헤더에 멤버 토큰 추가 예정")
	public ResponseMessage<CartResponse> getCart() {
		CartResponse response = new CartResponse(1L, 100L, 2);
		return ResponseMessage.ok(response);
	}
}
