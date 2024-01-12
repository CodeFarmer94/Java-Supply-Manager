package com.events;

import com.entities.Product;

public class ProduceProductEvent {

	
	private Product product;
	private int quantity;
	
	public ProduceProductEvent(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
}
