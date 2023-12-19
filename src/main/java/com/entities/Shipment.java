package com.entities;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.JoinColumn;


@Entity
public class Shipment implements Serializable {


	private static final long serialVersionUID = 1L;
	
	
	/*----------  Entity Column fields --------*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/* Many shipments can belong to a customer */
	@NotNull
	@ManyToOne
	private Customer customer;
	
	@NotNull
	@ManyToOne
	private Supplier supplier;
	
	@Temporal(TemporalType.DATE)
	private LocalDate date;


	/* Each shipment must hold many products */
	@NotEmpty
	@ManyToMany
	@JoinTable(
		    name = "shipment_product",
		    joinColumns = @JoinColumn(name = "shipment_id"),
		    inverseJoinColumns = @JoinColumn(name = "product_id")
		)
    private List<Product> shipmentProductList;
	
	
	
	/* --------- Constructors ---------*/
	
	
	public Shipment() {
		
	}
	
	public Shipment(Customer customer, LocalDate date, List<Product> shipmentList) {

		this.customer = customer;
		this.date = date;
		this.shipmentProductList = shipmentList;
	}
	
	
	/* ---------- Getters and Setters --------- */
	
	
	public long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public List<Product> getShipmentList() {
		return shipmentProductList;
	}

	public void setShipmentList(List<Product> shipmentList) {
		this.shipmentProductList = shipmentList;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
