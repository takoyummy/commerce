package com.practice.commerce.common.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "redis.lock")
public class RedisLockConfig {

	private Duration waitTime;
	private Duration leaseTime;
}