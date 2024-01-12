package com.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.primefaces.PrimeFaces;

import com.entities.Component;
import com.entities.Product;
import com.entities.ProductComponent;
import com.entities.ProductInventory;
import com.events.ProduceProductEvent;
import com.exceptions.MissingComponentsException;
import com.interfaces.InventoryService;


import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import com.qualifiers.ProductInventoryServiceQualifier;

@Stateless
public class ProductFactoryService {

	
	@Inject
	private GenericEntityService<Product> productService;
	

	@Inject @ProductInventoryServiceQualifier
	private ProductInventoryService productInventoryService;
	
	
	@Inject
	private ProcessComponentsService processComponentsService;
	
	@Inject
	private Event<ProduceProductEvent> produceProductEvent;
	
	public void produceProduct(Product product, int productQuantity) throws MissingComponentsException {
		
		     if(!this.areComponentsInStockSufficient(product, productQuantity)) {
		    	 throw new MissingComponentsException("Missing components for production process");
		     }
		     
		     produceProductEvent.fire(new ProduceProductEvent(product, productQuantity));
		     
	}
	
	public boolean areComponentsInStockSufficient(Product product, int quantity) {
		
		Set<Entry<Component, Integer>> requirementsEntryList =  this.calculateRequiredComponentsForProductQuantity(product, quantity).entrySet();
		Map<Component,Integer> inventoryStock = processComponentsService.getComponentInventoryQuantitiesMap();
		
		return requirementsEntryList.stream().allMatch(e -> {
			int requiredQuantity = e.getValue();
			int inStockQuantity;
			Component requiredComponent = e.getKey();
			
			if(inventoryStock.containsKey(requiredComponent)) {
				inStockQuantity = inventoryStock.get(requiredComponent);
			}
			else {
				System.out.println("Quantity is zero");
				inStockQuantity = 0;
			}
		
			return requiredQuantity <= inStockQuantity;
			
			});
	}
	
	
	
	
	
	private Map<Component, Integer> calculateRequiredComponentsForProductQuantity(Product product, int productQuantity) {
		
		Product foundProduct = this.productService.findById(Product.class, product.getId());
	    List<ProductComponent> list = foundProduct.getComponentsList2();
	    Map<Component, Integer> map = new HashMap<>();
	    if (list != null) {
	        list.forEach(e -> {
	            map.put(e.getComponent(), e.getQuantity() * productQuantity);
	        });
	    }
	    return map;
	}
	
	
	
	
	public Map<Component, Integer> calculateRequiredComponentsForProduct(Product product) {
		
		Product foundProduct = this.productService.findById(Product.class, product.getId());
	    List<ProductComponent> list = foundProduct.getComponentsList2();
	    Map<Component, Integer> map = new HashMap<>();
	    if (list != null) {
	        list.forEach(e -> {
	            map.put(e.getComponent(), e.getQuantity());
	        });
	    }
	    return map;
	}

	
}
