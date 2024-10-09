package com.practice.commerce.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "장바구니에 추가할 상품 요청 객체")
public class CartRequest {

	@Schema(description = "상품 ID", example = "100", required = true)
	private Long productId;

	@Schema(description = "상품 수량", example = "2", required = true)
	private Integer quantity;
}
