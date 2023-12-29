package com.beans.itemBeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;


import com.beans.GenericQuantityMap;
import com.entities.Product;
import com.services.AsyncProcessService;
import com.services.ProcessComponentsService;
import com.services.ProductFactoryService;
import com.services.ProductionProcess;
import com.services.PushContextService;
import com.services.WebSocketMessage;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Asynchronous;
import jakarta.enterprise.concurrent.ManagedExecutorService;

import jakarta.enterprise.context.SessionScoped;

import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
@SessionScoped
public class ProductFactoryBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private GenericQuantityMap<Product> quantityMapService;
	@Inject 
	private ProductFactoryService productFactoryService;
	@Inject 
	private ProcessComponentsService processComponentsService;
	@Resource
	private ManagedExecutorService executor;
	
	@Inject
	private PushContextService pushContextService;
	
	private Map<Product, ProductionProcess> activeProcessQueue;
	

		
	
	
	@PostConstruct
	public void init() {
		
		this.quantityMapService.setQuantityMap(new HashMap<Product, Integer>());
		this.activeProcessQueue = new HashMap<>();
	}
	

	   @Asynchronous
	    public void  updateProgressAsync(Product product, int quantity) {
		   
	          	
	            
	            executor.execute( ()-> {
	            	
	            	int progress = 0;
	            	activeProcessQueue.put(product, new ProductionProcess(product, 0, "Started"));
	            	while(progress <= 100) {
	            		try {
	            			if(progress == 100) {
	            				productFactoryService.produceProduct(product, quantity);
	        	  	            activeProcessQueue.put(product, new ProductionProcess(product, 100, "Completed"));
	            				this.sendMessage(new WebSocketMessage(String.valueOf(progress), getProductProcessId(product), "Completed", progress));
	            				break;
	            			}
	            			System.out.println(progress);
							Thread.sleep(1000);
							progress+=10;
							this.sendMessage(new WebSocketMessage(String.valueOf(progress), getProductProcessId(product), "In Progress", progress));
		            	
						} catch (InterruptedException e) {
							e.printStackTrace();
					}
          	}
	            	 
	  	        
	            });
	          
	            
	            
	            System.out.println("process completed");

	       
	    }
	   
	   
	
		public void sendMessage(Object message) {
		    pushContextService.sendMessage(message);
		}


	public Map<Product, ProductionProcess> getActiveProcessQueue() {
		return activeProcessQueue;
	}



	public void setActiveProcessQueue(Map<Product, ProductionProcess> activeProcessQueue) {
		this.activeProcessQueue = activeProcessQueue;
	}


	public String getProductProcessStatus(Product product) {
		
		ProductionProcess foundProcess = this.activeProcessQueue.get(product);
		if(foundProcess == null) {
			return "Not Started";
		}
		
		return foundProcess.getProcessStatus();
	
		
	}
	
public int getProductProcessProgress(Product product) {
		
		ProductionProcess foundProcess = this.activeProcessQueue.get(product);
		if(foundProcess == null) {
			return 0;
		}
		
		return foundProcess.getProgress();
	
		
	}
	public String getProductProcessId(Product product) {
		
		ProductionProcess foundProcess = this.activeProcessQueue.get(product);
		if(foundProcess == null) {
			return "";
		}
		return String.valueOf(foundProcess.getProcessId());
		
		
	}
	   
	   
	   
	public Map<Product, ProductionProcess> getProccessQueue() {
		return activeProcessQueue;
	}



	public void setProccessQueue(Map<Product, ProductionProcess> proccessQueue) {
		this.activeProcessQueue = proccessQueue;
	}


	public Set<Product> getProductSetFromMap(){
		return this.quantityMapService.getQuantityMap().keySet();
	}
	
	public int getProductQuantityFromMap(Product product) {
		
		return this.quantityMapService.getItemQuantity(product);
	}
	
	
	public void handleProduceProduct(Product product, int quantity) {
		
		productFactoryService.produceProduct(product, quantity);
	}
	
	
	

	
	public ProductFactoryService getProductFactoryService() {
		return productFactoryService;
	}




	public void setQuantityMapService(GenericQuantityMap<Product> quantityMapService) {
		this.quantityMapService = quantityMapService;
	}




	public GenericQuantityMap<Product> getQuantityMapService() {
		return quantityMapService;
	}


	public ProcessComponentsService getProcessComponentsService() {
		return processComponentsService;
	}



	
	
}
