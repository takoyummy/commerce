package com.practice.commerce.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.commerce.api.dto.request.BalanceRequest;
import com.practice.commerce.api.dto.response.BalanceResponse;
import com.practice.commerce.api.usecase.BalanceUseCase;
import com.practice.commerce.api.usecase.OrderUseCase;
import com.practice.commerce.common.dto.ResponseMessage;
import com.practice.commerce.common.security.UserId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/balance")
@Tag(name = "Balance API", description = "잔액 충전 및 조회와 관련된 API")
@RequiredArgsConstructor
public class BalanceController {

	private final BalanceUseCase balanceUseCase;

	@PostMapping
	@Operation(summary = "잔액 충전", description = "사용자의 잔액을 입력한 금액만큼 충전합니다.",
		security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseMessage chargeBalance(@UserId Long userId, @RequestBody BalanceRequest balanceRequest) {

		balanceUseCase.chargeBalance(userId, balanceRequest.getAmount());

		return ResponseMessage.ok();
	}

	@GetMapping
	@Operation(summary = "잔액 조회", description = "사용자의 잔액을 조회합니다.",
		security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseMessage checkBalance(@UserId Long userId) {

		BalanceResponse balanceResponse = balanceUseCase.checkBalance(userId);
		return ResponseMessage.ok(balanceResponse);
	}
}
