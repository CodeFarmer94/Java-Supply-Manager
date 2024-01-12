package com.services;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.entities.Component;
import com.entities.ComponentInventory;
import com.entities.Customer;
import com.entities.ExpenseInvoice;
import com.entities.Product;
import com.entities.ProductInventory;
import com.entities.ProfitInvoice;
import com.interfaces.InventoryService;


import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import com.qualifiers.ProductInventoryServiceQualifier;
import com.qualifiers.SellProductEvent;

@Stateless
public class SellProductService {


	@Inject
	private transient Logger logger;
	private static final long serialVersionUID = 1L;

	@Inject
	private GenericEntityService<Product> productService;
	@Inject
	private BalanceService balanceService;
	@Inject
	private ProfitInvoiceService profitInvoiceService;
	
	
	@Inject @ProductInventoryServiceQualifier
	private InventoryService<Product, ProductInventory> productInventoryService;
	
	@Inject @SellProductEvent
	private Event<Map<Product, Integer>> sellProductEvent;
	
	public void sellProducts(Map<Product, Integer> quantityMap, Customer customer) {
		
		Set<Product> productSet = quantityMap.keySet();
		
		sellProductEvent.fire(quantityMap);
	
		
		profitInvoiceService.save(new ProfitInvoice( productSet, customer));
	}
	
}
