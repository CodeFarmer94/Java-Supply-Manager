package com.beans.itemBeans;

import java.io.Serializable;
import java.util.List;

import com.entities.ComponentInventory;
import com.services.GenericEntityService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@ViewScoped
@Named
public class ComponentInventoryBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private GenericEntityService<ComponentInventory> componentInventoryService;
	private List<ComponentInventory> inventoryList;
	
	@PostConstruct
	public void init() {
		this.setInventoryList(componentInventoryService.findAll(ComponentInventory.class));
		
	}

	public List<ComponentInventory> getInventoryList() {
		return inventoryList;
	}

	public void setInventoryList(List<ComponentInventory> inventoryList) {
		this.inventoryList = inventoryList;
	}
	
}
