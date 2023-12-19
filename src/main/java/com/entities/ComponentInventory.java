package com.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: ComponentInventory
 *
 */
@Entity
public class ComponentInventory extends EntityImpl {

	
	private static final long serialVersionUID = 1L;

	@OneToOne
	private Component component;
	
	@NotNull
	private int quantity;
	
	
	
	public ComponentInventory(Component component, @NotNull int quantity) {
		super();
		this.component = component;
		this.quantity = quantity;
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




}
