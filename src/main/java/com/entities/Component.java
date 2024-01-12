package com.entities;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;


import com.interfaces.EntityInterface;
import com.interfaces.ItemInterface;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity

public class Component extends ItemBase implements EntityInterface, ItemInterface {

	private static final long serialVersionUID = 1L;

	/* ---------- Entity column fields ------- */

	/*
	 * A foreign key will be created representing the supplier(default supplier_id)
	 */
	@NotNull
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Supplier transactionParty;


	@ManyToMany(mappedBy = "expInvoiceList")
	private List<ExpenseInvoice> invoiceList;

	/*---------- Constructors ------------*/

	public Component() {

	}

	/*
	 * You can create a component without providing the list of products where its
	 * being used
	 */
	public Component(String name, double price, String description, Supplier supplier) {
		super(name, price, description);
		this.transactionParty = supplier;
		this.invoiceList = new ArrayList<>();

	}

	/*---------- Getters and Setters ------------*/

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Component component = (Component) o;
		return Objects.equals(id, component.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public List<ExpenseInvoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<ExpenseInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}


	public Supplier getTransactionParty() {
		return transactionParty;
	}

	public void setTransactionParty(Supplier transactionParty) {
		this.transactionParty = transactionParty;
	}

	@Override
	public String toString() {
		return "Component [id=" + id + ", name=" + name + ", price=" + price + ", supplier="
				+ transactionParty.getName() + "]";
	}

	public Long getTransactionPartyId() {
		return transactionParty != null ? transactionParty.getId() : null;
	}

	public String getTransactionPartyName() {
		return transactionParty != null ? transactionParty.getName() : null;
	}

}
