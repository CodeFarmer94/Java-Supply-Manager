package com.entities;



import com.interfaces.ItemInterface;
import com.interfaces.TransactionParty;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;


/**
 * Entity implementation class for Entity: productInventory
 *
 */
@Entity
public class ProductInventory extends EntityImpl implements ItemInterface{

	
	private static final long serialVersionUID = 1L;
	
	

	@OneToOne
	private Product product;

	
	private int quantity;
	
	
	
	public ProductInventory(Product product,  int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	
	public double getValue() {
		return product.getPrice() * quantity;
	}
	
	
	public Product getProduct() {
		return product;
	}

	public void setproduct(Product product) {
		this.product = product;
	}

	public ProductInventory() {
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
		return product.getName();
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return product.getPrice();
	}

	@Override
	public TransactionParty getTransactionParty() {
		return product.getTransactionParty();
	}


	

	


}
