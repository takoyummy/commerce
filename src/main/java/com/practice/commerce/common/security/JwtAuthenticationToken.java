package com.practice.commerce.common.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private final Long memberId;

	public JwtAuthenticationToken(Long memberId) {
		super(null);
		this.memberId = memberId;
		setAuthenticated(true); // 권한이 없는 상태에서 인증된 것으로 설정 (주의: 필요시 false로 설정)
	}

	public JwtAuthenticationToken(Long memberId, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.memberId = memberId;
		setAuthenticated(true); // 권한이 있는 상태에서 인증된 것으로 설정
	}

	@Override
	public Object getCredentials() {
		return null; // credentials는 사용하지 않음
	}

	@Override
	public Object getPrincipal() {
		return this.memberId; // memberId를 principal로 반환
	}
}
