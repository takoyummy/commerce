package com.practice.commerce.infrastructure.adapter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.practice.commerce.domain.order.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExternalDataPlatformAdapter {
	public void sendOrderData(Order order) {
		log.info("Sending order data to external platform: {}", order);
	}
}
