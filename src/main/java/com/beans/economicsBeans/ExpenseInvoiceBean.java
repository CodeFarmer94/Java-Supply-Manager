package com.beans.economicsBeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import java.util.logging.Logger;

import com.entities.Component;
import com.entities.ExpenseInvoice;
import com.entities.Supplier;

import com.services.ExpenseInvoiceService;
import com.services.GenericEntityService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;


public class ExpenseInvoiceBean implements Serializable{

	private static final long serialVersionUID = 1L;

	
	/* -------------- Bean instance fields ------------*/
	private Supplier supplier;
	
	/* This field will be populated by the user input - Check JSF page */
	private List<Long> invoiceComponentIdsList;
	
	private LocalDate date;
	
	private double amount;
	
	/* ------- Service Injections ----------- */
	
	@Inject 
	private ExpenseInvoiceService expenseInvoiceService;
	
	@Inject GenericEntityService<Component> componentService;
	
	@Inject
	private Logger logger;
	
	
	/*------ Business Logic Methods --------- */
	
	
	
	public void save () {
		
		try {	
				/* Finding list of component using user provided id */
				List<Component> invoiceComponentList = componentService.findListById(Component.class, invoiceComponentIdsList);
				
				/* Creating new Invoice */
				expenseInvoiceService.save(new ExpenseInvoice( invoiceComponentList , supplier));
				
		
			
			
		}catch (EntityNotFoundException e) {
            logger.warning(e.getMessage());
      
        }catch ( ConstraintViolationException e ) {
		     logger.warning("Exception during user registration: " + e.getMessage());
		
		} catch (PersistenceException e) {
	        logger.warning("PersistenceException occurred: " + e.getMessage());

	    } 
	}
	
		
	/* ------ Getters and Setters ------- */
	

	public Supplier getSupplier() {
		return supplier;
	}

	public List<Long> getInvoiceComponentIdsList() {
		return invoiceComponentIdsList;
	}

	public LocalDate getDate() {
		return date;
	}


	public double getAmount() {
		return amount;
	}





	public void setAmount(double amount) {
		this.amount = amount;
	}


	public Logger getLogger() {
		return logger;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public void setInvoiceComponentIdsList(List<Long> invoiceComponentIdsList) {
		this.invoiceComponentIdsList = invoiceComponentIdsList;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}



	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	
}
