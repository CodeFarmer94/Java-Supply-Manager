package com.beans.itemBeans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.primefaces.PrimeFaces;

import com.beans.GenericDataTableBean;
import com.beans.sorting_beans.SortingItemBean;
import com.entities.Component;
import com.entities.ComponentInventory;
import com.entities.Product;
import com.entities.Supplier;
import com.services.GenericEntityService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

@ViewScoped
@Named
public class ComponentInventoryBean extends GenericDataTableBean<ComponentInventory> {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient Logger logger;

	@Inject
	private SortingItemBean<ComponentInventory> sortBean;

	@PostConstruct
	public void init() {

		this.entityClass = ComponentInventory.class;

		super.init();
	}

	public double getTotalValue() {

		return this.entityList.stream().mapToDouble(e -> e.getComponent().getPrice() * e.getQuantity()).sum();

	}

	public int getTotalQuantities() {

		return this.entityList.stream().mapToInt(e -> e.getQuantity()).sum();
	}

	/* INVOKING SORT METHOD FROM THE SORTBEAN AND UPDATING LIST */
	public void sort() {
		try {
			List<ComponentInventory> sortedList = sortBean.sortList(entityList);
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

	public SortingItemBean<ComponentInventory> getSortBean() {
		return sortBean;
	}

	public void setSortBean(SortingItemBean<ComponentInventory> sortBean) {
		this.sortBean = sortBean;
	}

	public List<SelectItem> getFilterOptions() {
		return Arrays.asList(new SelectItem("", "Select"), new SelectItem("id", "ID"), new SelectItem("name", "Name"),
				new SelectItem("quantity", "Quantity"));
	}

}
