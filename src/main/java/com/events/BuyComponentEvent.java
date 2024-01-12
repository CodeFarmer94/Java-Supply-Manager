package com.events;


import java.util.Map;

import com.entities.Component;

public class BuyComponentEvent {

	
	private Map<Component, Integer> quantityMap;
	
	public BuyComponentEvent(Map<Component, Integer> quantityMap) {
		this.quantityMap = quantityMap;
	}
	
	public Map<Component, Integer> getQuantityMap() {
		return quantityMap;
	}
	
	public void setQuantityMap(Map<Component, Integer> quantityMap) {
		this.quantityMap = quantityMap;
	}
	
}
