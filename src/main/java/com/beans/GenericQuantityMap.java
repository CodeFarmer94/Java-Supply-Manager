package com.beans;

import java.io.Serializable;

import java.util.Map;
import java.util.logging.Logger;

import com.entities.ItemBase;

import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
public class GenericQuantityMap<T extends ItemBase>  implements Serializable{
	
	
	@Inject
	private transient Logger logger;
	private static final long serialVersionUID = 1L;
	
	
	private Map<T, Integer> quantityMap;
	private boolean showAdded = false;
	
	public void addToCart(T T) {
	    logger.info("T added");
	    try {
	        if (quantityMap.containsKey(T)) {
	            int prevQuantity = quantityMap.get(T);
	            quantityMap.put(T, prevQuantity + 1);
	        } else {
	           
	            quantityMap.put(T, 1);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void removeFromCart(T T) {
		logger.info("removing item" + T.getName());
		try {
			if(quantityMap.containsKey(T)) {
				int prevQuantity = quantityMap.get(T);
				if(prevQuantity == 1) {
					quantityMap.remove(T);
				}
				else {
				quantityMap.put(T, prevQuantity - 1);
				}
			}
		} catch( Exception e) {
			
		}
	}

	public int getItemQuantity(T T) {
		if(quantityMap.containsKey(T)) {
			return quantityMap.get(T); 
		} else {
			return 0;
		}
	}
	
	public boolean isItemAdded(T T) {
		if(quantityMap.containsKey(T)) {
			return true;
		}
		return false;
	}
	
	
	public double getTotalCost() {
		
		return quantityMap.entrySet().stream()
		        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
		        .sum();

	}
	
	public int getTotalQuantities() {
		
		return quantityMap.entrySet().stream().
				mapToInt(entry -> entry.getValue()).sum();
	}
	
	
	public void toggleShowAdded() {
		System.out.println("setting show added");
	    setShowAdded(!showAdded);
	    
	}

	

	public Map<T, Integer> getQuantityMap() {
		return quantityMap;
	}

	public void setQuantityMap(Map<T, Integer> quantityMap) {
		this.quantityMap = quantityMap;
	}

	public boolean isShowAdded() {
		return showAdded;
	}

	public void setShowAdded(boolean showAdded) {
		this.showAdded = showAdded;
	}



}
