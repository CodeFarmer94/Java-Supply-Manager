package com.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.openjpa.persistence.jdbc.Unique;

import com.interfaces.EntityInterface;
import com.interfaces.TransactionParty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
public class Supplier  extends EntityImpl implements Serializable, TransactionParty {

	
	private static final long serialVersionUID = 1L;
	
	
	@NotNull
	@Size(min = 5)
	@Unique
	private String name;
	
	@NotNull
	private String contactPhone;
	
	@NotNull
	private String email;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	
	/* Supplier holds a list of components it can supply  */
	@OneToMany(mappedBy = "transactionParty")
	private List<Component> componentList;
	
	
	@OneToMany(mappedBy = "supplier")
	private List<Shipment> shipmentList;
	
	
	
	/* -------- Constructors ------ */
	
	public Supplier() {
		super();
	}
	
	public Supplier(String name, Address address, String contactPhone, String email) {
		
		this.name = name;
		this.address = address;
		this.contactPhone = contactPhone;
		this.email = email;
		this.shipmentList = new ArrayList<>();
		this.componentList = new ArrayList<>();
		
	}
	
	  @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Supplier supplier = (Supplier) o;
	        return Objects.equals(id, supplier.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

	
	
	/* -------- Getters and Setters ------- */
	
	
	public List<Shipment> getShipmentList() {
		return shipmentList;
	}

	public void setShipmentList(List<Shipment> shipmentList) {
		this.shipmentList = shipmentList;
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

	public String getContactPhone() {
		return contactPhone;
	}

	public List<Component> getComponentList() {
		return componentList;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setComponentList(List<Component> componentList) {
		this.componentList = componentList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
