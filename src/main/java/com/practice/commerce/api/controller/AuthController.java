package com.practice.commerce.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.commerce.common.dto.ResponseMessage;
import com.practice.commerce.common.security.JwtTokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth API", description = "auth 관련 API")
public class AuthController {
	private final JwtTokenProvider jwtTokenProvider;

	public AuthController(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping("/token")
	@Operation(summary = "userId를 통해 token 발급", description = "사용자의 userId를 받아와서 토큰을 발급합니다.")
	public ResponseMessage<String> token(@RequestParam String userId) {
		String accessToken = jwtTokenProvider.createAccessToken(userId);
		return ResponseMessage.ok(accessToken);
	}
}
