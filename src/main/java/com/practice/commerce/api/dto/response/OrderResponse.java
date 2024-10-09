package com.practice.commerce.api.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "주문 응답 객체")
public class OrderResponse {

	@Schema(description = "사용자 ID", example = "1")
	private Long userId;

	@Schema(description = "주문 ID", example = "1")
	private String orderId;

	@Schema(description = "주문한 상품 목록")
	private List<OrderedProduct> products;

	@Data
	@AllArgsConstructor
	@Schema(description = "주문한 상품 정보")
	public static class OrderedProduct {
		@Schema(description = "상품 ID", example = "100")
		private Long productId;

		@Schema(description = "상품 수량", example = "2")
		private Integer quantity;

		@Schema(description = "상품 이름", example = "Product Name")
		private String productName;
	}

}
