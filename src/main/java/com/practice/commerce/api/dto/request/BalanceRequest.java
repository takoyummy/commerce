package com.practice.commerce.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "잔액 충전 요청을 위한 데이터 전송 객체")
public class BalanceRequest {
	@Schema(description = "충전 또는 소진할 금액", example = "100.00", required = true)
	private long amount;
}
