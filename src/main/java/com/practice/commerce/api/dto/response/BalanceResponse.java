package com.practice.commerce.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "잔액 조회 결과를 위한 데이터 전송 객체")
public class BalanceResponse {
	@Schema(description = "사용자 ID", example = "1")
	private Long userId;

	@Schema(description = "현재 잔액", example = "150.00")
	private Long currentBalance;

}
