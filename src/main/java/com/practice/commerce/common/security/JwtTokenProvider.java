package com.practice.commerce.common.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 토큰 생성 및 유효성 검증을 위한 클래스
 * refreshToken 구현은 시간 관계 상 뺐습니다.
 */
@Slf4j
@Component
public class JwtTokenProvider {
	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long accessTokenExpirationTime;

	public String createAccessToken(String userId) {
		Claims claims = Jwts.claims().setSubject(userId);
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessTokenExpirationTime);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		return Long.valueOf(Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody()
			.getSubject());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.debug("JWT Token is expired");
			return false;
		} catch (SignatureException e) {
			log.debug("JWT Token signature is invalid");
			return false;
		} catch (Exception e) {
			log.debug("JWT Token validation failed");
			return false;
		}
	}
}
