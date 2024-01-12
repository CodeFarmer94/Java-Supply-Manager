package com.beans.itemBeans;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.primefaces.PrimeFaces;

import com.beans.GenericDataTableBean;
import com.beans.GenericQuantityMap;
import com.beans.sorting_beans.SortingItemBean;
import com.entities.Product;
import com.entities.ProductInventory;
import com.entities.ProductInventory;

import jakarta.annotation.PostConstruct;

import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ProductInventoryBean extends GenericDataTableBean<ProductInventory> {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private SortingItemBean<ProductInventory> sortBean;
	
	@Inject
	private GenericQuantityMap<Product> quantityMapService;
	public GenericQuantityMap<Product> getQuantityMapService() {
		return quantityMapService;
	}


	public void setQuantityMapService(GenericQuantityMap<Product> quantityMapService) {
		this.quantityMapService = quantityMapService;
	}


	@PostConstruct
	public void init() {
		
		this.entityClass = ProductInventory.class;
		super.init();
	}
	
	
	public void handleCreateEntity() {
		
	}
	public void resetFields() {
		
	}
	
	
	

	public double getTotalValue() {

		return this.entityList.stream().mapToDouble(e -> e.getProduct().getPrice() * e.getQuantity()).sum();

	}

	public int getTotalQuantities() {

		return this.entityList.stream().mapToInt(e -> e.getQuantity()).sum();
	}
	
	

	/* INVOKING SORT METHOD FROM THE SORTBEAN AND UPDATING LIST */
	public void sort() {
		try {
			List<ProductInventory> sortedList = sortBean.sortList(entityList);
			this.entityList = sortedList;
			PrimeFaces.current().ajax().update("form:datatable");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	/* Add sorting and filtering options */
	public List<SelectItem> getSortOptions() {
		return Arrays.asList(new SelectItem("", "Select"), new SelectItem("id", "ID"), new SelectItem("name", "Name"),
				new SelectItem("price", "Price"), new SelectItem("quantity", "Quantity")

		);
	}

	public SortingItemBean<ProductInventory> getSortBean() {
		return sortBean;
	}

	public void setSortBean(SortingItemBean<ProductInventory> sortBean) {
		this.sortBean = sortBean;
	}

	public List<SelectItem> getFilterOptions() {
		return Arrays.asList(new SelectItem("", "Select"), new SelectItem("id", "ID"), new SelectItem("name", "Name"),
				new SelectItem("quantity", "Quantity"));
	}
}
