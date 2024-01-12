package com.DTO;

import java.util.List;



public class ProductDTO {

	private String name;
	private double price;
	private String description;
	private String code;
	private List<Long> componentsIds;
	
	
	public ProductDTO() {
		super();
	}

	public ProductDTO(String name, double price, String description, String code, List<Long> componentsIds) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.code = code;
		this.componentsIds = componentsIds;
	}
	
	// Getters and setters
	public String getName() {
		return name;
	}


	public double getPrice() {
		return price;
	}


	public String getDescription() {
		return description;
	}


	public String getCode() {
		return code;
	}


	public List<Long> getComponentsIds() {
		return componentsIds;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public void setComponentsIds(List<Long> componentsIds) {
		this.componentsIds = componentsIds;
	}	
	
}
