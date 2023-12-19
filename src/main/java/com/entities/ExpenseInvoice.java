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

public class ExpenseInvoice implements Serializable, Invoice {

	private static final long serialVersionUID = 1L;
	
	/* ------- Entity Column fields ---------- */
	 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@OneToOne
	@JoinColumn( name = "supplier_id", unique = true )
	private Supplier supplier;

	@NotNull
	@Positive
	private double amount;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	private LocalDate date;
	
	@ManyToMany
	@JoinTable(
	        name = "expenseInvoice_components",
	        joinColumns = @JoinColumn(name = "expenseInvoice_id"),
	        inverseJoinColumns = @JoinColumn(name = "component_id"))
	private List<Component> expInvoiceList;  

	


	/* ------ Constructors -------- */ 
	
	
	public ExpenseInvoice() {
		super();
	}
	
	public ExpenseInvoice(@NotNull @Positive double amount, @NotNull @NotNull LocalDate date, @NotNull List<Component> expInvoiceList, Supplier supplier) {
		super();
		this.amount = amount;
		this.date = date;
	    this.expInvoiceList = expInvoiceList;
		this.supplier = supplier;
	}

	
	/* -------- Getters and Setters ------- */
	
	

	public double getAmount() {
		return amount;
	}






	public void setAmount(double amount) {
		this.amount = amount;
	}




	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Component> getItemList(){
		return this.expInvoiceList;
		
	}
	public void setItemList(List<Component> expInvoiceList) {
		this.expInvoiceList = expInvoiceList;
	}
	
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
