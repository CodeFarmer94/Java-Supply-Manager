package com.beans.itemBeans;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.List;

import org.primefaces.PrimeFaces;

import com.beans.GenericDataTableBean;
import com.beans.sorting_beans.SortingItemBean;
import com.entities.Component;
import com.entities.Supplier;
import com.services.GenericEntityService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;


@Named
@ViewScoped
public class ComponentBean extends GenericDataTableBean<Component>{

	
	private static final long serialVersionUID = 1L;
	
	
	/* ------ Services Injection ------*/
	
	
	@Inject
	private SortingItemBean<Component> sortBean;
	@Inject 
	private GenericEntityService<Supplier> supplierService;
	@Inject
	private transient Logger logger;
	
	
	/* ---- Bean instance fields ------- */
	
	
	private String name;
	private double price;
	private int quantity;
	private String description;
	private Supplier selectedSupplier;
	

	 @PostConstruct
	    public void init() {
		 	/* THIS MUST BE INITIALIZED TO USE SUPERCLASS METHODS */
	        this.entityClass = Component.class;
	        super.init();
	    }


	/* ------ Create a new component with details and default quantity zero */
	
	 public void saveEntity() {
			
		logger.info("registering component...");
		
		try {	
			/* Save component with supplier foreign key */
			entityService.save(new Component(name, price, description, selectedSupplier));
	        this.refreshEntityList();
	        this.resetFields();
	     
	    } catch (PersistenceException e) {
	        logger.warning("PersistenceException occurred: " + e.getMessage());
	       
	    } catch (ConstraintViolationException e) {
	        logger.warning("Exception during user registration: " + e.getMessage());
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	

	
	public List<SelectItem> getSupplierSelectItems() {
        return supplierService.findAll(Supplier.class).stream()
                .map(entity -> new SelectItem(entity.getId(), entity.getName()))
                .collect(Collectors.toList());
    }
	
	
	/* Add sorting and filtering options */
    public List<SelectItem> getSortOptions() {
        return Arrays.asList(
            new SelectItem("", "Select"),
            new SelectItem("id", "ID"),
            new SelectItem("name", "Name"),
            new SelectItem("price", "Price"),
            new SelectItem("quantity", "Quantity"),
            new SelectItem("party_id", "Supplier_ID"),
            new SelectItem("party_name", "Supplier_Name")
            
        );
    }

    public List<SelectItem> getFilterOptions() {
        return Arrays.asList(
            new SelectItem("", "Select"),
            new SelectItem("id", "ID"),
            new SelectItem("name", "Name"),
            new SelectItem("quantity", "Quantity")
        );
    }
    
    public void sort() {
        try {
            logger.info("Sort Column: " + sortBean.getSelectedSortColumn());
            logger.info("Sort Order: " + sortBean.getSelectedSortOrder());
            List<Component> sortedList = sortBean.sortList(entityList);
            this.entityList = sortedList;
            PrimeFaces.current().ajax().update("form");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    
    public void resetFields() {
        this.name = null;
        this.price = 0.0; // Assuming a default value for price
        this.selectedSupplier  = null;
        this.quantity = 0;
        this.description = null;
    }

	

	/* ---------- Getters and Setters ---------- */
	
	
	
	
	public String getName() {
		return name;
	}



	public SortingItemBean<Component> getSortBean() {
		return sortBean;
	}



	public GenericEntityService<Supplier> getSupplierService() {
		return supplierService;
	}



	public void setSortBean(SortingItemBean<Component> sortBean) {
		this.sortBean = sortBean;
	}



	public void setSupplierService(GenericEntityService<Supplier> supplierService) {
		this.supplierService = supplierService;
	}



	public double getPrice() {
		return price;
	}







	public void setName(String name) {
		this.name = name;
	}



	public void setPrice(double price) {
		this.price = price;
	}







	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}









	public Supplier getSelectedSupplier() {
		return selectedSupplier;
	}



	public void setSelectedSupplier(Supplier selectedSupplier) {
		this.selectedSupplier = selectedSupplier;
	}

	
	

}
