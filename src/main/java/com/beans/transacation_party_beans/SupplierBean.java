package com.beans.transacation_party_beans;


import java.util.Arrays;

import java.util.List;
import java.util.Set;

import org.apache.openjpa.persistence.InvalidStateException;
import org.primefaces.PrimeFaces;

import com.beans.GenericDataTableBean;
import com.beans.sorting_beans.SortingPartyBean;
import com.beans.utilityBeans.EditRowBean;
import com.beans.utilityBeans.FilterBean;
import com.entities.Address;
import com.entities.Component;
import com.entities.Product;
import com.entities.Supplier;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;



@Named
@ViewScoped
public class SupplierBean extends GenericDataTableBean<Supplier>{

	private static final long serialVersionUID = 1L;




	@Inject
	private SortingPartyBean<Supplier> sortBean;
	
	


	/* Bean Fields */
	   private String name;
	    private String street;
	    private String city;
	    private String zipCode;
	    private String country;
	    private String contactPhone;
	    private String email;
	    
	   

	 @PostConstruct
	    public void init() {
		 	/* THIS MUST BE INITIALIZED TO USE SUPERCLASS METHODS */
	        this.entityClass = Supplier.class;
	        super.init();
	    }
	

	/* ------- POST ----------*/ 

	public void saveEntity() {
		
		logger.info("registering supplier...");
	    try {
	    	
	    	Address address = new Address(street, city, zipCode, country);
	    	Supplier supplier = new Supplier(name, address, contactPhone, email);
	        entityService.save( supplier );
	        
	        /* UPDATING THE LIST AND THE VIEW */
	        
	        this.entityList = entityService.findAll(entityClass); // Update the list
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Supplier Registered"));
	        PrimeFaces.current().executeScript("PF('createDialog').hide()");
	        PrimeFaces.current().ajax().update("form:datatable");

	        this.resetFields();

	     
	    } catch (PersistenceException e) {
	        logger.warning("PersistenceException occurred: " + e.getMessage());
	       
	    } catch (ConstraintViolationException e) {
	        logger.warning("Exception during user registration: " + e.getMessage());
	        
	    }
	}
	


    @Transactional
    public String calculateComponentListSize(Supplier supplier) {
    	Supplier foundSupplier = entityService.findById(entityClass, supplier.getId());
        List<Component> componentsList = foundSupplier.getComponentList();
        return String.valueOf(componentsList.size());
    }


	public void sort() {
		try {
		List<Supplier> sortedList = sortBean.sortList(entityList);
		this.entityList = sortedList;
		 PrimeFaces.current().ajax().update("form:datatable");
		 } catch( IllegalArgumentException e) {
			 e.printStackTrace();
		 }
		
	}
	

	  
	/* Reset Bean fields */
	public void resetFields() {
		this.name = null;
		this.contactPhone = null;
		this.email = null;
		this.contactPhone = null;
		this.street = null;
		this.city = null;
		this.country = null;
		this.city = null;
		this.zipCode = null;
		PrimeFaces.current().ajax().update("dialogs:create-panel");
		
	}
	
	
	
	/* DEFINE SORT OPTIONS AND FILTER OPTIONS */
    public List<SelectItem> getSortOptions() {
       
        return Arrays.asList(
            new SelectItem("", "Select"),
            new SelectItem("id", "ID"),
            new SelectItem("name", "Name"),
            new SelectItem("email", "Email"),
            new SelectItem("street", "Street"),
            new SelectItem("zipCode", "ZipCode"),
            new SelectItem("country", "Country")
        );
    }

    public List<SelectItem> getFilterOptions() {
    
        return Arrays.asList(
            new SelectItem("", "Select"),
            new SelectItem("id", "ID"),
            new SelectItem("name", "Name"),
            new SelectItem("email", "Email")
        );
    }

	/* ------------- Getters and Setters -------- */

	
	
	
	public String getName() {
		return name;
	}

	

	public void setName(String name) {
		this.name = name;
	}


	



	public String getContactPhone() {
		return contactPhone;
	}




	public String getEmail() {
		return email;
	}




	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public void setEmail(String email) {
		this.email = email;
	}

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }





	public FilterBean<Supplier> getFilterBean() {
		return filterBean;
	}



	public void setFilterBean(FilterBean<Supplier> filterBean) {
		this.filterBean = filterBean;
	}



	public EditRowBean<Supplier> getEditRowBean() {
		return editRowBean;
	}



	public void setEditRowBean(EditRowBean<Supplier> editRowBean) {
		this.editRowBean = editRowBean;
	}


	public SortingPartyBean<Supplier> getSortBean() {
		return sortBean;
	}


	public void setSortBean(SortingPartyBean<Supplier> sortBean) {
		this.sortBean = sortBean;
	}
	

	
}
