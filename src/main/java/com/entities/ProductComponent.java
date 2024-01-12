package com.entities;

import com.interfaces.EntityInterface;
import com.interfaces.ItemInterface;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductComponent extends EntityImpl{

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = CascadeType.PERSIST) // or CascadeType.ALL
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    private int quantity;

 
    public ProductComponent() {
		
	}

	public ProductComponent(Product product, Component component, int quantity) {
		super();
		this.product = product;
		this.component = component;
		this.quantity = quantity;
	}

	public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
