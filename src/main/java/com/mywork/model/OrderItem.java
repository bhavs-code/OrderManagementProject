package com.mywork.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderItemId;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	public OrderItem(int quantity, Order order, Product product) {
		super();
		this.quantity = quantity;
		this.order = order;
		this.product = product;
	}


	
}
