package com.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.interfaces.EntityInterface;
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

public class ExpenseInvoice extends EntityImpl {

	private static final long serialVersionUID = 1L;
	
	/* ------- Entity Column fields ---------- */
	 

	@NotNull
	@OneToOne
	private Supplier supplier;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

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
	
	public ExpenseInvoice(@NotNull List<Component> expInvoiceList, Supplier supplier) {
		super();
	
		this.createdAt = LocalDateTime.now();
	    this.expInvoiceList = expInvoiceList;
		this.supplier = supplier;
	}

	
	/* -------- Getters and Setters ------- */
	
	






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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public List<Component> getExpInvoiceList() {
		return expInvoiceList;
	}

}
