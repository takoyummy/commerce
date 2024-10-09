package com.practice.commerce.api.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주문 요청 객체")
public class OrderRequest {
	@Schema(description = "주문할 상품 목록", required = true)
	private List<OrderedProduct> products;

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "주문할 상품 정보")
	public static class OrderedProduct {

		@Schema(description = "상품 ID", example = "100", required = true)
		private Long productId;

		@Schema(description = "주문할 상품 수량", example = "2", required = true)
		private Integer quantity;
	}
}
