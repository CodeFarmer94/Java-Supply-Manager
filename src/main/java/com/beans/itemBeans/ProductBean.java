package com.beans.itemBeans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.openjpa.persistence.InvalidStateException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.beans.GenericDataTableBean;
import com.beans.GenericQuantityMap;
import com.beans.sorting_beans.SortingItemBean;
import com.entities.Component;
import com.entities.Product;
import com.entities.ProductComponent;
import com.entities.Supplier;
import com.services.CreateProductService;
import com.services.GenericEntityService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@Named
@ViewScoped
public class ProductBean extends GenericDataTableBean<Product> {

	private static final long serialVersionUID = 1L;

	/*----- Bean instance fields ------*/

	private String name;
	private double price;
	private String description;
	private int selectQuantity;


	/*------ Service Injections -------*/
	@Inject
	private GenericQuantityMap<Component> quantityMapService;
	
	@Inject
	private SortingItemBean<Product> sortBean;

	@Inject 
	private CreateProductService createProductService;
	
	@Inject
	private transient Logger logger;

	
	@PostConstruct
	public void init() {
		this.entityClass = Product.class;
		quantityMapService.setQuantityMap(new HashMap<Component, Integer>());
		super.init();
	}

	/* -------- Business logic methods -------- */

	@Transactional
	public void handleCreateProduct() {
		logger.info("Saving entity Product...");

		try {
			Product newProduct = new Product(name, price, description);
			createProductService.createProductWithComponents(newProduct, quantityMapService.getQuantityMap());
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Product Registered"));
			this.refreshEntityList();
		    this.resetFields();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	/* Add sorting and filtering options */
	public List<SelectItem> getSortOptions() {
		return Arrays.asList(new SelectItem("", "Select"), new SelectItem("id", "ID"), new SelectItem("name", "Name"),
				new SelectItem("price", "Price"), new SelectItem("quantity", "Quantity"));
	}

	public List<SelectItem> getFilterOptions() {
		return Arrays.asList(new SelectItem("", "Select"), new SelectItem("id", "ID"), new SelectItem("name", "Name"),
				new SelectItem("quantity", "Quantity"));
	}

	/* INVOKING SORT METHOD FROM THE SORTBEAN AND UPDATING LIST */
	public void sort() {
		try {
			List<Product> sortedList = sortBean.sortList(entityList);
			this.entityList = sortedList;
			PrimeFaces.current().ajax().update("form:datatable");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}

	public void resetFields() {
		this.name = null;
		this.price = 0.0;
		
		this.description = null;
		quantityMapService.setQuantityMap(new HashMap<Component, Integer>());
	}

	/* --------- Getters and Setters ---------- */


	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public SortingItemBean<Product> getSortBean() {
		return sortBean;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSortBean(SortingItemBean<Product> sortBean) {
		this.sortBean = sortBean;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}




	public int getSelectQuantity() {
		return selectQuantity;
	}

	public void setSelectQuantity(int selectQuantity) {
		this.selectQuantity = selectQuantity;
	}

	public GenericQuantityMap<Component> getQuantityMapService() {
		return quantityMapService;
	}

	public void setQuantityMapService(GenericQuantityMap<Component> quantityMapService) {
		this.quantityMapService = quantityMapService;
	}

}
