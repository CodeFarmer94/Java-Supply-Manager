package com.beans.itemBeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

import com.beans.GenericQuantityMap;
import com.entities.Component;
import com.entities.Product;
import com.exceptions.MissingComponentsException;
import com.services.ProcessComponentsService;
import com.services.ProcessWebSocketMessage;
import com.services.ProductFactoryService;
import com.services.ProductionProcess;
import com.services.PushContextService;


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

	private Map<Component, Integer> inStockComponentMap;

	@PostConstruct
	public void init() {

		this.quantityMapService.setQuantityMap(new HashMap<Product, Integer>());
		this.activeProcessQueue = new ConcurrentHashMap<>();
		this.inStockComponentMap = processComponentsService.getComponentInventoryQuantitiesMap();
	}

	public Map<Component, Integer> getInStockComponentMap() {
		return inStockComponentMap;
	}

	@Asynchronous
	public void updateProgressAsync(Product product, int quantity) {

		executor.execute(() -> {

			int progress = 0;
			activeProcessQueue.put(product, new ProductionProcess(product, 0, "In Progress"));
			while (progress <= 100) {
				try {
					if (progress == 100) {
						
						try {
							productFactoryService.produceProduct(product, quantity);
							refreshInStockComponentMap();
							setProcessStatus(product, "Completed");
							setProcessProgress(product, 100);
							sendMessage(product, 100, "Completed");
									
							Thread.sleep(1000);
							activeProcessQueue.remove(product);
							break;
						} catch( MissingComponentsException e) {
							e.printStackTrace();
							setProcessStatus(product, "Failed");
							setProcessProgress(product, 0);
							sendMessage(product, 0, "Failed");
							break;
						}
						
					}
					Thread.sleep(1000);
					progress += 10;
					sendMessage(product, progress, "In Progress");

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		});

	}

	public void sendMessage(Product product, int progress, String status) {
		ProcessWebSocketMessage message = new ProcessWebSocketMessage(getProductProcessId(product), status, progress);
		pushContextService.sendMessage(message);
	}

	public void refreshInStockComponentMap() {
		this.inStockComponentMap = processComponentsService.getComponentInventoryQuantitiesMap();
	}

	public String getProductProcessStatus(Product product) {

		ProductionProcess foundProcess = this.activeProcessQueue.get(product);
		if (foundProcess == null) {
			return "Not Started";
		}

		return foundProcess.getProcessStatus();

	}

	public int getProductProcessProgress(Product product) {

		ProductionProcess foundProcess = this.activeProcessQueue.get(product);
		if (foundProcess == null) {
			return 0;
		}

		return foundProcess.getProgress();

	}

	public String getProductProcessId(Product product) {

		ProductionProcess foundProcess = this.activeProcessQueue.get(product);
		if (foundProcess == null) {
			return "";
		}
		return String.valueOf(foundProcess.getProcessId());

	}
	
	public String getStartProductionButtonText(Product product) {
		
		boolean areComponentsSufficient = productFactoryService.areComponentsInStockSufficient(product, this.getProductQuantityFromMap(product));
		if (this.getProductProcessStatus(product).equals("Completed")) {
			return "Completed";
		} 
		if (this.getProductProcessStatus(product).equals("In Progress")) {
			return "In Progress";
		}
		if(areComponentsSufficient) {
			return "Start Production";
		} else {
			return "Missing Components";
		}
	}
	
	public boolean isStartProductionButtonDisabled(Product product) {
		return !productFactoryService.areComponentsInStockSufficient(product, this.getProductQuantityFromMap(product)) 
				|| !this.getProductProcessStatus(product).equals("Not Started");
			
	}
	
	
	public void setProcessProgress(Product product, int progress) {
		ProductionProcess foundProcess = this.activeProcessQueue.get(product);
		if (foundProcess == null) {
			return;
		}
		foundProcess.setProgress(progress);
	}
	
	public void setProcessStatus(Product product, String status) {
		ProductionProcess foundProcess = this.activeProcessQueue.get(product);
		if (foundProcess == null) {
			return;
		}
		foundProcess.setProcessStatus(status);
	}
	
	
	
	
	
	
	
	
	
	public Map<Product, ProductionProcess> getProccessQueue() {
		return activeProcessQueue;
	}

	public void setProccessQueue(Map<Product, ProductionProcess> proccessQueue) {
		this.activeProcessQueue = proccessQueue;
	}

	public Set<Product> getProductSetFromMap() {
		return this.quantityMapService.getQuantityMap().keySet();
	}

	public int getProductQuantityFromMap(Product product) {

		return this.quantityMapService.getItemQuantity(product);
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
	public Map<Product, ProductionProcess> getActiveProcessQueue() {
		return activeProcessQueue;
	}

	public void setActiveProcessQueue(Map<Product, ProductionProcess> activeProcessQueue) {
		this.activeProcessQueue = activeProcessQueue;
	}
}
