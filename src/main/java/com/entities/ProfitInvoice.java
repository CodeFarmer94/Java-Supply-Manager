package com.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.interfaces.Invoice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entity implementation class for Entity: ExpenseInvoice
 *
 */
@Entity

public class ProfitInvoice extends EntityImpl implements Invoice {

	private static final long serialVersionUID = 1L;

	/* ------- Entity Column fields ---------- */

	@OneToOne
	private Customer customer;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;

	
	@ManyToMany
	@JoinTable(name = "profitInvoice_components", joinColumns = @JoinColumn(name = "profitInvoice_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Set<Product> proInvoiceList;
	
	private double totalAmount;
	
	

	/* ------ Constructors -------- */

	public ProfitInvoice() {
		super();
	}

	public ProfitInvoice(Set<Product> itemList, Customer customer) {
		super();
	
		this.createdAt = LocalDateTime.now();
		this.proInvoiceList = itemList;
		this.customer = customer;
		this.totalAmount = itemList.stream().mapToDouble(Product::getPrice).sum();
	}
	public ProfitInvoice(Set<Product> itemList, Customer customer, LocalDateTime createdAt) {
		super();
	
		this.createdAt = createdAt;
		this.proInvoiceList = itemList;
		this.customer = customer;
		this.totalAmount = itemList.stream().mapToDouble(Product::getPrice).sum();
		
	}

	/* -------- Getters and Setters ------- */



	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public Set<Product> getProInvoiceList() {
		return proInvoiceList;
	}

	public void setProInvoiceList(Set<Product> proInvoiceList) {
		this.proInvoiceList = proInvoiceList;
	}


	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	

}