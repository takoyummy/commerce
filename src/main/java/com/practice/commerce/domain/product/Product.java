package com.practice.commerce.domain.product;

import com.practice.commerce.common.exception.InventoryShortageException;
import com.practice.commerce.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Long price;

	@Column(nullable = false)
	private Integer stock;

	public void reduceStock(int quantity) {
		if (this.stock < quantity) {
			throw new InventoryShortageException();
		}
		this.stock -= quantity;
	}

	public static Product createProduct(Long productId, String name, Long price, Integer stock) {
		Product product = new Product();
		product.productId = productId;
		product.name = name;
		product.price = price;
		product.stock = stock;
		return product;
	}
}
