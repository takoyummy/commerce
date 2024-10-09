package com.practice.commerce.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "상품 응답 객체")
public class ProductResponse {

	@Schema(description = "상품 ID", example = "1")
	private Long productId;

	@Schema(description = "상품 이름", example = "Sample Product")
	private String name;

	@Schema(description = "상품 가격", example = "100.00")
	private Double price;

	@Schema(description = "상품 재고 수량", example = "10")
	private Integer stock;
}