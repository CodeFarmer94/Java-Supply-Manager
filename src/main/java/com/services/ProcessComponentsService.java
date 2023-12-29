package com.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import com.entities.Component;
import com.entities.ComponentInventory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

@Stateless
public class ProcessComponentsService {
	
	
	@Inject
	private transient Logger logger;
	@Inject
	private GenericEntityService<ComponentInventory> componentInventoryService;
	
	public void consumeComponents(Component componentToBeConsumed, int componentQuantityToBeConsumed) {
		
	
			ComponentInventory inventoryEntry = componentInventoryService.findById( ComponentInventory.class, componentToBeConsumed.getId());
			
			if( inventoryEntry == null) {
				throw new EntityNotFoundException("Component not found in inventory");
			}
			if ( inventoryEntry.getQuantity() < componentQuantityToBeConsumed) {
				throw new IllegalStateException("Instock component not sufficient for process");
			}
			
			int prevQuantity = inventoryEntry.getQuantity();
			inventoryEntry.setQuantity(prevQuantity - componentQuantityToBeConsumed);
			
			componentInventoryService.update(inventoryEntry);
			
		
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
}
