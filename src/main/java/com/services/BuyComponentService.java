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

import jakarta.ejb.Stateless;
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
	private GenericEntityService<Component> componentService;
	@Inject
	private BalanceService balanceService;
	@Inject
	private GenericEntityService<ExpenseInvoice> expenseInvoiceService;
	@Inject
	private GenericEntityService<ComponentInventory> componentInventoryService;

	
	
	
	
	
	public void buyComponents(Map<Component, Integer> quantityMap) {

		try {
			if (isBalanceInsufficient(quantityMap)) {
				throw new IllegalStateException("Insufficient balance cant buy components");
			}
			addComponentsToInventory(quantityMap);
			createInvoicesFromMap(quantityMap);
			payComponentsTotalCost(quantityMap);


		} catch (PersistenceException e) {
			logger.warning("PersistenceException occurred: " + e.getMessage());

		} catch (ConstraintViolationException e) {
			logger.warning("Exception during user registration: " + e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createInvoicesFromMap(Map<Component, Integer> quantityMap) {

		Set<Component> list = quantityMap.keySet();
		Map<Supplier, List<Component>> map = list.stream()
				.collect(Collectors.groupingBy(Component::getTransactionParty));
		map.entrySet().forEach(entry -> {
			this.expenseInvoiceService.save(new ExpenseInvoice(entry.getValue(), entry.getKey()));

		});

	}

	public void addComponentsToInventory(Map<Component, Integer> quantityMap) {
		
		List<ComponentInventory> inventoryList = componentInventoryService.findAll(ComponentInventory.class);

		quantityMap.entrySet().forEach(entry -> {
			
		    Optional<ComponentInventory> found = inventoryList.stream()
		            .filter(e -> e.getComponent().getName().equals(entry.getKey().getName()))
		            .findFirst();

		    found.ifPresent(existingInventory -> {
		        existingInventory.setQuantity(existingInventory.getQuantity() + entry.getValue());
		        componentInventoryService.update(existingInventory);
		    });

		    if (!found.isPresent()) {
		        componentInventoryService.save(new ComponentInventory(entry.getKey(), entry.getValue()));
		    }

		});
	}
	
	

	public void payComponentsTotalCost(Map<Component, Integer> quantityMap) {

		this.balanceService.save(new Balance(getRemainingBalanceAfterBuy(quantityMap),
				"Component Purchase: " + this.getTotalCost(quantityMap)));
	
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
		return balanceAmount - this.getTotalCost(quantityMap) < 0 ? true : false;
	}

	
	

	public GenericEntityService<Component> getComponentService() {
		return componentService;
	}
	public GenericEntityService<Balance> getBalanceService() {
		return balanceService;
	}
	public GenericEntityService<ExpenseInvoice> getExpenseInvoiceService() {
		return expenseInvoiceService;
	}
	public GenericEntityService<ComponentInventory> getComponentInventoryService() {
		return componentInventoryService;
	}

	
}
