package com.practice.commerce.domain.cart;

import com.practice.commerce.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "cart_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class CartItem extends BaseEntity {

	@Id
	@Column(nullable = false)
	private Long cartId;

	@Id
	@Column(nullable = false)
	private Long productId;

	@Column(nullable = false)
	private Integer quantity;
}
