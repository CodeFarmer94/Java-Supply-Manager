package com.beans.economicsBeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import java.util.logging.Logger;

import com.entities.Component;
import com.entities.ExpenseInvoice;
import com.entities.Supplier;
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
	private GenericEntityService<ExpenseInvoice> expenseInvoiceService;
	
	@Inject GenericEntityService<Component> componentService;
	
	@Inject
	private Logger logger;
	
	
	/*------ Business Logic Methods --------- */
	
	
	
	public String createExpenseInvoice () {
		
		try {	
				/* Finding list of component using user provided id */
				List<Component> invoiceComponentList = componentService.findListById(Component.class, invoiceComponentIdsList);
				
				/* Creating new Invoice */
				expenseInvoiceService.save(new ExpenseInvoice(amount, date, invoiceComponentList , supplier));
				
				return "expense_invoice_success";
			
			
		}catch (EntityNotFoundException e) {
            logger.warning(e.getMessage());
            return "entity_not_found_exception";
        }catch ( ConstraintViolationException e ) {
		     logger.warning("Exception during user registration: " + e.getMessage());
		        return "constraint_violation_exception";
		} catch (PersistenceException e) {
	        logger.warning("PersistenceException occurred: " + e.getMessage());
	        return "persistence_exception";
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



	public GenericEntityService<Component> getComponentService() {
		return componentService;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}


	public void setComponentService(GenericEntityService<Component> componentService) {
		this.componentService = componentService;
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