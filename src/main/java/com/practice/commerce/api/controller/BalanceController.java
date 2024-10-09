package com.practice.commerce.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.commerce.api.dto.request.BalanceRequest;
import com.practice.commerce.api.dto.response.BalanceResponse;
import com.practice.commerce.common.dto.ResponseMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/balance")
@Tag(name = "Balance API", description = "잔액 충전 및 조회와 관련된 API")
public class BalanceController {

	@PostMapping("/charge")
	@Operation(summary = "잔액 충전", description = "사용자의 잔액을 입력한 금액만큼 충전합니다.")
	public ResponseMessage chargeBalance(@RequestBody BalanceRequest balanceRequest) {
		return ResponseMessage.ok();
	}

	@GetMapping("/check/{userId}")
	@Operation(summary = "잔액 조회", description = "사용자의 잔액을 조회합니다.")
	public ResponseMessage checkBalance(@PathVariable Long userId) {

		BalanceResponse balanceResponse = new BalanceResponse(userId, 150.00);
		return ResponseMessage.ok(balanceResponse);
	}
}
