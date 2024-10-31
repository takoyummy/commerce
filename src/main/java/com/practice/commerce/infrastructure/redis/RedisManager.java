package com.practice.commerce.infrastructure.redis;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.practice.commerce.common.RedisKeyPrefix;
import com.practice.commerce.common.config.RedisLockConfig;
import com.practice.commerce.common.exception.RedisLockException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisManager {
	private final RedisTemplate<Object, Object> redisTemplate;
	private final RedissonClient redissonClient;
	private final RedisLockConfig redisLockConfig;

	public void setValue(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public Object getValue(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void incrementValue(String key, long delta) {
		redisTemplate.opsForValue().increment(key, delta);
	}

	public void deleteKey(String key) {
		redisTemplate.delete(key);
	}

	public <T> T executeWithLock(String key, Supplier<T> task) {
		RLock lock = redissonClient.getLock(key);
		try {
			if (lock.tryLock(redisLockConfig.getWaitTime().toSeconds(), redisLockConfig.getLeaseTime().toSeconds(), TimeUnit.SECONDS)) {
				return task.get();
			} else {
				throw new RedisLockException("레디스 락 획득 실패: " + key);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RedisLockException("Error while handling lock", e);
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
			}
		}
	}

}
