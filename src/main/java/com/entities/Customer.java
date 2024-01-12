package com.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.interfaces.EntityInterface;
import com.interfaces.TransactionParty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Customer
 *
 */
@Entity

public class Customer extends EntityImpl implements Serializable, TransactionParty {

	
	private static final long serialVersionUID = 1L;
	
	
	/* ----------- Entity Column fields --------- */
	

	@NotNull
	@Size(min = 5)
	private String name;
	
	@NotNull
	private String contactPhone;
	
	@NotNull
	private String email;
	
	@OneToOne(cascade = CascadeType.ALL )
	private Address address;
	
	
	@OneToMany(mappedBy = "customer")
	private List<Shipment> shipmentList;
	

	@OneToMany(mappedBy = "transactionParty")
	private List<Product> productList;
	



	/* ------------- Constructors --------------- */

	public Customer() {
		super();
	}
    

	
	public Customer(@NotNull @Size(min = 5) String name, @NotNull String contactPhone, @NotNull String email,
			Address address) {
		super();
		this.name = name;
		this.contactPhone = contactPhone;
		this.email = email;
		this.address = address;
		this.productList = new ArrayList<>();
		this.shipmentList = new ArrayList<>();
	}



	/* -------- Getters and Setters ------- */
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Customer customer = (Customer) obj;
	    return Objects.equals(id, customer.id);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}



	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

	public List<Shipment> getShipmentList() {
		return shipmentList;
	}

	public String getContactPhone() {
		return contactPhone;
	}



	public String getEmail() {
		return email;
	}



	public List<Product> getProductList() {
		return productList;
	}



	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public void setShipmentList(List<Shipment> shipmentList) {
		this.shipmentList = shipmentList;
	}



	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}



	public void setShipments(List<Shipment> shipmentList) {
		this.shipmentList = shipmentList;
	}

}
