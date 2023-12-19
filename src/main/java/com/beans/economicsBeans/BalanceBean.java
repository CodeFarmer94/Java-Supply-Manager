package com.beans.economicsBeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import org.primefaces.PrimeFaces;

import com.entities.Balance;
import com.services.GenericEntityService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;


@Named
@ViewScoped
public class BalanceBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	/* ---- Bean instance fields ----- */

	/* Entity Fields*/
	

	private double balanceAmount;
	
	
	/* ---- JSF view fields ---- */
	
	private List<Balance> balanceList;
	private Balance currentBalance;
	private double fundsToAdd;
	
	/* ---- Services Injections ---- */
	
	@Inject
	private GenericEntityService<Balance> balanceService;
	@Inject
	private transient Logger logger;
	
	
	
	@PostConstruct
	public void init() {
		this.balanceList = balanceService.findAllSorted(Balance.class, "createdAt", "DESC");
		this.currentBalance = this.findLastBalance();
	
	}
	/*------- Business Logic Methods ------ */
	
	
	public void createBalance(String transactionType) {
	    try {
	    	
	        balanceService.save(new Balance(balanceAmount, transactionType));
	       
	        
	    } catch (ConstraintViolationException e) {
	        logger.warning("Exception during " + this.getClass().getSimpleName() + " registration: " + e.getMessage());
	      
	    } catch (PersistenceException e) {
	        logger.warning("PersistenceException occurred: " + e.getMessage());
	        
	    } 
	}
	
	
	public void addFunds() {
		
		try {
			
			/* Check if no balance is present  */
			if(currentBalance == null) {
			 balanceService.save(new Balance(fundsToAdd, "Added funds"));	
			} else {
				
				double prevAmount = this.currentBalance.getBalanceAmount();
				balanceService.save(new Balance(prevAmount + fundsToAdd, "Added funds: $" + fundsToAdd ));
			}
			
			/* RESET THE INPUT */
			this.fundsToAdd = 0;
			/* UPDATE THE CURRENT BALANCE */
			currentBalance = this.findLastBalance();
			this.balanceList = balanceService.findAllSorted(Balance.class, "createdAt", "DESC");
			
			 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Funds added to Balance"));
		        PrimeFaces.current().executeScript("PF('createDialog').hide()");
		        PrimeFaces.current().ajax().update("form:balanceAmount");
		        PrimeFaces.current().ajax().update("form:balanceTable");
			
		} catch (ConstraintViolationException e) {
	        logger.warning("Exception during " + this.getClass().getSimpleName() + " registration: " + e.getMessage());
	      
	    } catch (PersistenceException e) {
	        logger.warning("PersistenceException occurred: " + e.getMessage());
	        
	    } 
	}
	
	

	/* ------ FIND METHODS ------- */
	
	public Balance findLastBalance() {
		logger.info("Finding most recent balance...");
		try {
			String columnName = "createdAt";
			return balanceService.findLastByColumn(Balance.class, columnName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getLastUpdateDate() {
	
     
      return this.getDateAsString(this.currentBalance.getCreatedAt());
	}
	
	public String getDateAsString(LocalDateTime dateTime) {
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd / HH:mm:ss");
	      return dateTime.format(formatter);
	}
	
	
	
	
	
	/* -------- Getters and Setters -------- */

	public double getBalanceAmount() {
		return balanceAmount;
	}


	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
		
		
	}
	public GenericEntityService<Balance> getBalanceService() {
		return balanceService;
	}

	public void setBalanceService(GenericEntityService<Balance> balanceService) {
		this.balanceService = balanceService;
	}
	
	public Balance getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Balance currentBalance) {
		this.currentBalance = currentBalance;
	}


	public double getFundsToAdd() {
		return fundsToAdd;
	}


	public void setFundsToAdd(double fundsToAdd) {
		this.fundsToAdd = fundsToAdd;
	}


	public List<Balance> getBalanceList() {
		return balanceList;
	}


	public void setBalanceList(List<Balance> balanceList) {
		this.balanceList = balanceList;
	}


}
