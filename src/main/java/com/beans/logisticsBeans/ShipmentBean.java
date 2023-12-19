package com.beans.logisticsBeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import com.entities.Customer;
import com.entities.Product;
import com.entities.Shipment;
import com.services.GenericEntityService;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;


@Named
@RequestScoped
public class ShipmentBean  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	/* ----- Bean instance fields ------*/
	
	private Customer customer;

	private LocalDate date;
	
	
	/* This field will be populated by the user input - Check JSF page */
	private List<Long> selectedProductIdsList;
	
	
	/* ------- Services Injections ---------- */
	
	@Inject 
	private GenericEntityService<Shipment> shipmentService;
	
	@Inject
	private GenericEntityService<Product> productService;
	@Inject
	private transient Logger logger;
	
	
	
	/* ---- Business logic operations ----- */
	
	
	
	public String createShipment() {
		
	
		try {
			
			List<Product> shippingProductList = productService.findListById(Product.class, selectedProductIdsList);
			shipmentService.save(new Shipment(customer, date, shippingProductList));
			return "shipment_create_success";
			
			
		} catch (EntityNotFoundException e) {
            logger.warning(e.getMessage());
            return "entity_not_found_exception";
        }catch ( ConstraintViolationException e ) {
		     logger.warning("Exception during shipment registration: " + e.getMessage());
		        return "constraint_violation_exception";
		} catch (PersistenceException e) {
	        logger.warning("PersistenceException occurred: " + e.getMessage());
	        return "persistence_exception";
	    } 
	}
	
	
	
	
	/* ------ Getters and Setters ------ */
	
	public Customer getCustomer() {
		return customer;
		
	}


	public LocalDate getDate() {
		return date;
	}

	public List<Long> getSelectedProductIdsList() {
		return selectedProductIdsList;
	}

	public GenericEntityService<Shipment> getShipmentService() {
		return shipmentService;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setSelectedProductIdsList(List<Long> selectedProductIdsList) {
		this.selectedProductIdsList = selectedProductIdsList;
	}

	public void setShipmentService(GenericEntityService<Shipment> shipmentService) {
		this.shipmentService = shipmentService;
	}

	
	
}
