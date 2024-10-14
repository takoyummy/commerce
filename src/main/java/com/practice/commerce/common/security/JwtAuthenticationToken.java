package com.practice.commerce.common.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private final String memberId;
	private Object credentials;

	public JwtAuthenticationToken(String memberId) {
		super(null);
		this.memberId = memberId;
		setAuthenticated(true);
	}

	public JwtAuthenticationToken(String memberId, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.memberId = memberId;
		this.credentials = credentials;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.memberId;
	}
}
