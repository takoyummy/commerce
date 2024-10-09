package com.practice.commerce.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "장바구니 응답 객체")
public class CartResponse {
	@Schema(description = "사용자 ID", example = "1")
	private Long userId;

	@Schema(description = "상품 ID", example = "100")
	private Long productId;

	@Schema(description = "상품 수량", example = "2")
	private Integer quantity;
}