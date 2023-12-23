package com.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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

public class ProfitInvoice extends EntityImpl  implements Invoice {

	
	
	private static final long serialVersionUID = 1L;
	
	
	
	/* ------- Entity Column fields ---------- */
	 
	

	@OneToOne
	@JoinColumn(name = "customer_id", unique = true)
	private Customer customer;

	@NotNull
	@Positive
	private double amount;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private LocalDate date;
	
	
	@ManyToMany
	@JoinTable(
	        name = "profitInvoice_components",
	        joinColumns = @JoinColumn(name = "profitInvoice_id"),
	        inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> proInvoiceList;  
	
	
	
	/* ------ Constructors -------- */ 
	
	
	public ProfitInvoice() {
		super();
	}
	
	public ProfitInvoice(@NotNull @Positive double amount, @NotNull @NotNull LocalDate date, @NotNull List<Product> itemList, Customer customer) {
		super();
		this.amount = amount;
		this.date = date;
		this.proInvoiceList = itemList;
		this.customer = customer;
	}

	
	/* -------- Getters and Setters ------- */
	
	

	public double getAmount() {
		return amount;
	}



	public LocalDate getDate() {
		return date;
	}


	public List<Product> getProInvoiceList() {
		return proInvoiceList;
	}

	public void setProInvoiceList(List<Product> proInvoiceList) {
		this.proInvoiceList = proInvoiceList;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}



	public void setDate(@NotNull LocalDate date) {
		this.date = date;
	}




	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


}