package com.beans.economicsBeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.primefaces.PrimeFaces;

import com.beans.GenericQuantityMap;
import com.beans.itemBeans.ComponentInventoryBean;
import com.entities.Balance;
import com.entities.Component;
import com.entities.ComponentInventory;
import com.entities.ExpenseInvoice;
import com.entities.Shipment;
import com.entities.Supplier;
import com.services.BuyComponentService;
import com.services.GenericEntityService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@Named
@ViewScoped
public class BuyComponentBean implements Serializable {

	@Inject
	private transient Logger logger;
	private static final long serialVersionUID = 1L;

	@Inject
	private GenericQuantityMap<Component> quantityMapService;
	
	@Inject
	private BuyComponentService buyComponentService;
	
	
	private String orderStatus;
	
	
	
	@PostConstruct
	public void init() {
		quantityMapService.setQuantityMap(new HashMap<Component, Integer>());
		orderStatus = "Pending";
	}
	
	

	  public Map<Component, Integer> getCartMap(){
		  return quantityMapService.getQuantityMap();
	  }
	  
	  public void handleBuyAction() {
	        try {
	        	
	            buyComponentService.buyComponents(quantityMapService.getQuantityMap());
	            quantityMapService.getQuantityMap().clear();
	            orderStatus = "Order successfully placed.";
	            FacesContext.getCurrentInstance().addMessage(null,
			            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Purchased Component"));
		
	        } catch (Exception e) {
	            logger.warning("Error during component purchase: " + e.getMessage());
	            orderStatus = "Failed to place order.";
	           
	        }
	    }
	    
	 public boolean isBalanceInsufficient(){
		  
		 return buyComponentService.isBalanceInsufficient(getCartMap());
		 
	 }
	 
	 public double getRemainingBalanceAfterBuy() {
		 return buyComponentService.getRemainingBalanceAfterBuy(getCartMap());
	 }
	 
	 
	 public Set<Supplier> getSupplierSetFromMap(){
	
		 return this.buyComponentService.groupComponentsBySupplier(getCartMap()).keySet();
	 }
	 
	 public List<Component> getComponentListForSupplier(Supplier supplier){
		 return this.buyComponentService.groupComponentsBySupplier(getCartMap()).get(supplier);
	 }
	 
	  
		public String getOrderStatus() {
			return orderStatus;
		}

		public void resetOrderStatus() {
			
			orderStatus = "Pending";
		}
		
		public void emptyCart() {
			quantityMapService.getQuantityMap().clear();
		}
	  
	public GenericQuantityMap<Component> getQuantityMapService() {
		return quantityMapService;
	}
	
}
