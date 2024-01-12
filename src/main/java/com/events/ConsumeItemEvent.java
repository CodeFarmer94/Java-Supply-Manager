package com.events;

import com.interfaces.ItemInterface;

public class ConsumeItemEvent<T extends ItemInterface> {

	
	public T item;
	public int quantity;
	
	public ConsumeItemEvent(T item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
