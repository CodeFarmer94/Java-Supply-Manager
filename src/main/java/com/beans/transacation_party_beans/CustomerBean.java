package com.beans.transacation_party_beans;

import java.util.Arrays;
import java.util.List;
import org.primefaces.PrimeFaces;

import com.beans.GenericDataTableBean;
import com.beans.sorting_beans.SortingPartyBean;
import com.entities.Address;
import com.entities.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;


@Named
@SessionScoped
public class CustomerBean extends GenericDataTableBean<Customer>{
	
	
	private static final long serialVersionUID = 1L;
	

	@Inject
	private SortingPartyBean<Customer> sortBean;
	
	/* ------ Bean instance fields ------ */
	

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
	        this.entityClass = Customer.class;
	        super.init();
	    }
	
		/* ------- POST ----------*/ 

		public void saveEntity() {
			
			logger.info("registering customer...");
		    try {
		    	
		    	Address address = new Address(street, city, zipCode, country);
		    	entityService.save(new Customer( name, contactPhone, email, address));
		       
		        
		        /* UPDATING THE LIST AND THE VIEW */
		        
		        this.entityList = entityService.findAll(entityClass); // Update the list
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", "Customer Registered"));

		        this.resetFields();

		     
		    } catch (PersistenceException e) {
		        logger.warning("PersistenceException occurred: " + e.getMessage());
		       
		    } catch (ConstraintViolationException e) {
		        logger.warning("Exception during user registration: " + e.getMessage());
		        
		    }
		}
		

		public void sort() {
			try {
			List<Customer> sortedList = sortBean.sortList(entityList);
			this.entityList = sortedList;
			 PrimeFaces.current().ajax().update("form:datatable");
			 } catch( IllegalArgumentException e) {
				 e.printStackTrace();
			 }
			
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
  	}
  	
	
	/* ------- Getters and Setters -------- */
	
	
	public String getName() {
		return name;
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

	public String getContactPhone() {
		return contactPhone;
	}

	public String getEmail() {
		return email;
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

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SortingPartyBean<Customer> getSortBean() {
		return sortBean;
	}

	public void setSortBean(SortingPartyBean<Customer> sortBean) {
		this.sortBean = sortBean;
	}


	
	
	
}
