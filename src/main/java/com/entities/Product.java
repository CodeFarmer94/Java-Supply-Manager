package com.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.interfaces.EntityInterface;
import com.interfaces.ItemInterface;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;


@Entity
public class Product extends ItemBase  implements EntityInterface, ItemInterface{

    private static final long serialVersionUID = 1L;
    
    
    /*--------- Entity column fields --------------*/

    


    @OneToMany(mappedBy = "product") 
    private List<ProductComponent> componentsList2;

    
	@ManyToMany(mappedBy = "shipmentProductList")
    private List<Shipment> shipments;
	
	@ManyToOne
	private Customer transactionParty;
	
	@ManyToMany(mappedBy = "proInvoiceList")
	private List<ProfitInvoice> invoiceList;

    
    /*---------------- Constructors ---------------*/
    
    



	public Product() {
        super();
    }


	public Product(String name, double price, String description) {
    	super( name, price,  description );
        this.shipments = new ArrayList<Shipment>();
        this.invoiceList = new ArrayList<>();
        
    }

	  
    /*-------------- Getters and setters -----------*/
	

	
    public Customer getTransactionParty() {
		return transactionParty;
	}


	public List<ProfitInvoice> getInvoiceList() {
		return invoiceList;
	}


	public void setTransactionParty(Customer transactionParty) {
		this.transactionParty = transactionParty;
	}


	public void setInvoiceList(List<ProfitInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
    
    
    
    public List<Shipment> getShipments() {
		return shipments;
	}



	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

   

}

