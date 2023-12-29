package com.beans.economicsBeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import org.primefaces.PrimeFaces;

import com.entities.Balance;
import com.services.BalanceService;
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


	
	/* ---- JSF view fields ---- */
	
	private List<Balance> balanceList;
	private Balance currentBalance;
	private double fundsToAdd;
	private double fundsToRemove;
	
	/* ---- Services Injections ---- */
	
	@Inject
	private BalanceService balanceService;

	
	
	
	@PostConstruct
	public void init() {
		this.balanceList = balanceService.findAllSorted(Balance.class, "createdAt", "DESC");
		this.currentBalance = balanceService.findLastBalance();
	
	}
	/*------- Business Logic Methods ------ */
	
	
	public void addFunds() {
		this.balanceService.addFunds(this.fundsToAdd);
		  FacesContext.getCurrentInstance().addMessage(null,
		            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "$"+ this.fundsToAdd + " Added to balance"));
		this.refreshBalancePage();
	}
	public void removeFunds() {
		this.balanceService.removeFunds(this.fundsToRemove);
		  FacesContext.getCurrentInstance().addMessage(null,
		            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "$"+ this.fundsToRemove + " Removed from balance"));
		this.refreshBalancePage();
	}
	
	
	public String getLastUpdateDate() {
	
     
      return this.getDateAsString(this.currentBalance.getCreatedAt());
	}
	
	public String getDateAsString(LocalDateTime dateTime) {
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd / HH:mm:ss");
	      return dateTime.format(formatter);
	}
	
	public void refreshBalancePage() {
		this.fundsToAdd = 0;
		this.fundsToRemove = 0;
		this.currentBalance = balanceService.findLastBalance();
		this.balanceList = balanceService.findAllSorted(Balance.class, "createdAt", "DESC");
	}
	
	
	
	
	/* -------- Getters and Setters -------- */



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


	public double getFundsToRemove() {
		return fundsToRemove;
	}


	public void setFundsToRemove(double fundsToRemove) {
		this.fundsToRemove = fundsToRemove;
	}


}
