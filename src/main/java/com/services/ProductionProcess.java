package com.services;

import java.util.UUID;

import com.entities.Product;

public class ProductionProcess {

	
	private Product product;
	private int progress;
	private String processStatus;
	private UUID processId;
	

	public ProductionProcess(Product product, int progress, String processStatus) {
		super();
		this.product = product;
		this.progress = progress;
		this.processStatus = processStatus;
		this.processId = UUID.randomUUID(); 
	}
	
	
	
	public UUID getProcessId() {
		return processId;
	}

	public void setProcessId(UUID processId) {
		this.processId = processId;
	}

	
	public Product getProduct() {
		return product;
	}

	public String getProcessStatus() {
		return processStatus;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}



	public int getProgress() {
		return progress;
	}



	public void setProgress(int progress) {
		this.progress = progress;
	}
}
