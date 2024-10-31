package com.practice.commerce.common.security;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Long.class) && parameter.hasParameterAnnotation(UserId.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String token = webRequest.getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Authorization header is missing or invalid");
		}

		token = token.substring(7);
		return jwtTokenProvider.getUserIdFromToken(token);
	}

}
