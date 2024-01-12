package com.entities;



import com.interfaces.ItemInterface;
import com.interfaces.TransactionParty;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;


/**
 * Entity implementation class for Entity: ComponentInventory
 *
 */
@Entity
public class ComponentInventory extends EntityImpl implements ItemInterface{

	
	private static final long serialVersionUID = 1L;
	@OneToOne
	private Component component;
	private int quantity;
	
	
	
	public ComponentInventory(Component component,  int quantity) {
		super();
		this.component = component;
		this.quantity = quantity;
	}

	
	public double getValue() {
		return component.getPrice() * quantity;
	}
	
	
	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public ComponentInventory() {
		super();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String getName() {
		return component.getName();
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return component.getPrice();
	}

	@Override
	public TransactionParty getTransactionParty() {
		return component.getTransactionParty();
	}

	


}
