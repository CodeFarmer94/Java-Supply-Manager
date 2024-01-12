package com.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.beans.GenericQuantityMap;
import com.beans.economicsBeans.BalanceBean;
import com.beans.itemBeans.ComponentInventoryBean;
import com.entities.Balance;
import com.entities.Component;
import com.entities.ComponentInventory;
import com.entities.ExpenseInvoice;
import com.entities.Supplier;
import com.events.BuyComponentEvent;
import com.interfaces.InventoryService;

import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

@Stateless
public class BuyComponentService implements Serializable {


	@Inject
	private transient Logger logger;
	private static final long serialVersionUID = 1L;


	@Inject
	private BalanceService balanceService;
	

	
	@Inject 
	private Event<BuyComponentEvent> buyComponentEvent; 
	
	public void buyComponents(Map<Component, Integer> quantityMap) {

		
			if (isBalanceInsufficient(quantityMap)) {
				throw new IllegalStateException("Insufficient balance cant buy components");
			}
			buyComponentEvent.fire(new BuyComponentEvent(quantityMap));
		
			

	}

	
	public Map<Supplier, List<Component>> groupComponentsBySupplier(Map<Component, Integer> quantityMap) {

		Set<Component> list = quantityMap.keySet();
		Map<Supplier, List<Component>> mapSupplierToComponents = list.stream()
				.collect(Collectors.groupingBy(Component::getTransactionParty));
		return mapSupplierToComponents;
		
	}


		


	public double getTotalCost(Map<Component, Integer> quantityMap) {
		
		return quantityMap.entrySet().stream()
		        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
		        .sum();

	}

	public double getRemainingBalanceAfterBuy(Map<Component, Integer> quantityMap) {

		double balanceAmount = balanceService.findLastBalance().getBalanceAmount();
		double newBalance = balanceAmount - this.getTotalCost(quantityMap);
		return newBalance;
	}

	public boolean isBalanceInsufficient(Map<Component, Integer> quantityMap) {

		double balanceAmount = balanceService.findLastBalance().getBalanceAmount();
		return balanceAmount - this.getTotalCost(quantityMap) <= 0 ? true : false;
	}

	
	

	
}
