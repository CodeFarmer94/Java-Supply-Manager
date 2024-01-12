package com.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import com.entities.Component;
import com.entities.ComponentInventory;
import com.entities.Product;
import com.entities.ProductComponent;
import com.events.ConsumeItemEvent;
import com.events.ProduceProductEvent;
import com.qualifiers.ComponentInventoryQualifier;

import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

@Stateless
public class ProcessComponentsService {
	
	
	@Inject
	private transient Logger logger;
	@Inject @ComponentInventoryQualifier
	private ComponentInventoryService componentInventoryService;
	
	@Inject
	private GenericEntityService<Product> productService;
	
	@Inject
	public Event<ConsumeItemEvent<Component>> consumeItemEvent;
	
	public void listenProduceProduct(@Observes ProduceProductEvent event) {

		Product product = event.getProduct();
		int productQuantity = event.getQuantity();

		Map<Component, Integer> requiredComponentsMap = calculateRequiredComponentsForProductQuantity(product,
				productQuantity);

		requiredComponentsMap.entrySet().forEach(entry -> {
			consumeItemEvent.fire(new ConsumeItemEvent<Component>(entry.getKey(), entry.getValue()));
			
		});

	}
	
	
	
	
	public Map<Component, Integer> getComponentInventoryQuantitiesMap (){
		
		Map<Component, Integer> inventoryMap = new HashMap<>();
		List<ComponentInventory> inventoryList = componentInventoryService.findAll(ComponentInventory.class);
		inventoryList.forEach(e-> inventoryMap.put(e.getComponent(), e.getQuantity()));
		
		return inventoryMap;
		
	}
	
	public int getComponentInStockQuantity(Component component) {
		return componentInventoryService.findById( ComponentInventory.class, component.getId()).getQuantity();
		
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
	
}
