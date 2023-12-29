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

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@Stateless
public class ProductFactoryService {

	
	@Inject
	private GenericEntityService<Product> productService;
	@Inject
	private ProcessComponentsService processComponentsService;

		
	public void produceProduct(Product product, int productQuantity) {
		
		try {
			 Set<Entry<Component, Integer>> requiredComponentSet = this.calculateRequiredComponentsForProductQuantity(product, productQuantity).entrySet();
			 requiredComponentSet.forEach(e -> {
				processComponentsService.consumeComponents(e.getKey(), e.getValue()); 
			 });
			 
		} catch(EntityNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			 e.printStackTrace();
			
		}	 
		 
	}
	
	
	public Map<Component, Integer> calculateRequiredComponentsForProductQuantity(Product product, int productQuantity) {
		
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
	
	
	public boolean areComponentsInStockSufficient(Product product, int quantity) {
		
		Set<Entry<Component, Integer>> requirementsEntryList =  this.calculateRequiredComponentsForProductQuantity(product, quantity).entrySet();
		Map<Component,Integer> inventoryStock = processComponentsService.getComponentInventoryQuantitiesMap();
		
		return requirementsEntryList.stream().allMatch(e -> {
			int requiredQuantity = e.getValue();
			int inStockQuantity;
			
			if(inventoryStock.containsKey(e)) {
				inStockQuantity = inventoryStock.get(e);
			}
			else {
				System.out.println("Quantity is zero");
				inStockQuantity = 0;
			}
		
			return requiredQuantity <= inStockQuantity;
			
			});
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
