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
import com.entities.Customer;
import com.entities.ExpenseInvoice;
import com.entities.Product;
import com.entities.Shipment;
import com.entities.Supplier;
import com.services.BuyComponentService;
import com.services.GenericEntityService;
import com.services.SellProductService;

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
public class SellProductBean implements Serializable {

	@Inject
	private transient Logger logger;
	private static final long serialVersionUID = 1L;

	@Inject
	private GenericQuantityMap<Product> quantityMapService;
	
    @Inject
    private SellProductService sellProductService;
	
	
	private String orderStatus;
	private Customer selectedCustomer;
	
	
	@PostConstruct
	public void init() {
		quantityMapService.setQuantityMap(new HashMap<Product, Integer>());
		orderStatus = "Pending";
	}
	
	

	  public Map<Product, Integer> getCartMap(){
		  return quantityMapService.getQuantityMap();
	  }
	  
	  public void handleSellAction() {
	        try {
	        	
	            sellProductService.sellProducts(getCartMap(), selectedCustomer);
	            quantityMapService.getQuantityMap().clear();
	            orderStatus = "Order successfully placed.";
	            FacesContext.getCurrentInstance().addMessage(null,
			            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Products sold successfully."));
		
	        } catch (Exception e) {
	            logger.warning("Error during product selling: " + e.getMessage());
	            orderStatus = "Failed to place order.";
	           
	        }
	    }
	  
	  
	 
	  
		public Customer getSelectedCustomer() {
			System.out.println("Selected Customer: " + selectedCustomer);
		return selectedCustomer;
	}



	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}



	public void setSelectedCustomer(Customer selectedCustomer) {
		System.out.println("Selected Customer: " + selectedCustomer);
		this.selectedCustomer = selectedCustomer;
	}



		public String getOrderStatus() {
			return orderStatus;
		}

		public void resetOrderStatus() {
			orderStatus = "Pending";
		}
	  
	public GenericQuantityMap<Product> getQuantityMapService() {
		return quantityMapService;
	}
	
}
