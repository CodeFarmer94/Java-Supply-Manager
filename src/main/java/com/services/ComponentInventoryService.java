package com.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.entities.Component;
import com.entities.ComponentInventory;
import com.entities.Product;
import com.events.BuyComponentEvent;
import com.events.ConsumeItemEvent;

import com.qualifiers.ComponentInventoryQualifier;

import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityNotFoundException;


@ComponentInventoryQualifier
public class ComponentInventoryService extends GenericEntityService<ComponentInventory> {

	public void addItems(@Observes BuyComponentEvent event ) {

		List<ComponentInventory> inventoryList = findAll(ComponentInventory.class);

		event.getQuantityMap().entrySet().forEach(entry -> {
			
		    Optional<ComponentInventory> found = inventoryList.stream()
		            .filter(e -> e.getComponent().getName().equals(entry.getKey().getName()))
		            .findFirst();

		    found.ifPresent(existingInventory -> {
		        existingInventory.setQuantity(existingInventory.getQuantity() + entry.getValue());
		        update(existingInventory);
		    });

		    if (!found.isPresent()) {
		        save(new ComponentInventory(entry.getKey(), entry.getValue()));
		    }

		});
		
	}


	public void consumeItems(@Observes ConsumeItemEvent<Component> event) {
		
	
			ComponentInventory inventoryEntry = findById( ComponentInventory.class, event.getItem().getId());
			
			if( inventoryEntry == null) {
				throw new EntityNotFoundException("Component not found in inventory");
			}
		
			if ( inventoryEntry.getQuantity() < event.getQuantity()) {
				throw new IllegalStateException("Instock " + inventoryEntry.getName() +" not sufficient for process");
			}
			
			int prevQuantity = inventoryEntry.getQuantity();
			inventoryEntry.setQuantity(prevQuantity - event.getQuantity());
			
			update(inventoryEntry);
			
		
	}

	
	public void addItems(Component item, int quantity) {
		// TODO Auto-generated method stub
		
	}

}
