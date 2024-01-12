package com.beans;

import java.io.Serializable;

import java.util.List;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.primefaces.PrimeFaces;

import com.beans.utilityBeans.EditRowBean;
import com.beans.utilityBeans.FilterBean;
import com.entities.Component;
import com.interfaces.EntityInterface;
import com.services.GenericEntityService;


import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

public abstract class  GenericDataTableBean<T extends EntityInterface> implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	/*---- CDI INJECTIONS   ---- */
	
	
	@Inject
	protected transient Logger logger;
	@Inject
	protected  GenericEntityService<T> entityService;
	@Inject
	protected FilterBean<T> filterBean;
	@Inject
	protected  EditRowBean<T> editRowBean;
	
	
	
	/* USED FOR DINAMICALLY DISPLAY ENTiTY DETAILS */
	private String entityIdParam;
	private T entityInstance;
	
	/* JSF SELECTED ENTITY LIST */
	
	private List<T> selectedEntityList;
	
	/* TO BE INITIALIZED FIELDS */
	
	protected List<T> entityList;

	

	/* MUST BE INITIALIZED IN CHILD CLASS */
	protected Class<T> entityClass;
	

	@PostConstruct
	public void init() {
		
		this.entityList = entityService.findAll( entityClass );
	}
	
	
	public abstract void resetFields() ;
	
	public abstract void handleCreateEntity();
	
	
	
	
	 public void saveEntity() {
			
		logger.info("registering component...");
		
		try {	
			
			
			this.handleCreateEntity();
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
	

	/* ------- FIND ----------*/
	
	public void findByIdParam() {
		this.entityInstance = entityService.findById(entityClass, Long.valueOf(entityIdParam));
	}
	
	public void refreshEntityList() {
		this.entityList = entityService.findAll( entityClass);
	}
	/* ------  DELETE ----------*/
	
	public void deleteSelectedEntities() {
	    logger.info("deleting selected suppliers...");
	    try {
	    	
	        List<Long> supplierIdList = selectedEntityList.stream().map(T::getId).collect(Collectors.toList());
	        
	        List<T> deletedEntities = entityService.removeListById(entityClass, supplierIdList);
	        this.selectedEntityList.clear();

	        // Log the deleted entities
	        if (!deletedEntities.isEmpty()) {
	            logger.info("Deleted Suppliers:");
	            for (T deletedEntity : deletedEntities) {
	                logger.info("ID: " + deletedEntity.getId() + ", Name: " + deletedEntity.getClass().getSimpleName());
	            }
	        }

	        // Update the supplierList after deletion
	        this.entityList = entityService.findAll(entityClass);
	        PrimeFaces.current().ajax().update("form:datatable");
	        PrimeFaces.current().ajax().update("form:delete-button");
	        
	        FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info Message", entityClass.getSimpleName()+ " Deleted"));
	    } catch (EntityNotFoundException e) {
	        logger.warning(e.getMessage());
	    } catch (PersistenceException e) {
	        logger.warning("PersistenceException occurred: " + e.getMessage());
	    }
	}
	
	
	/* -------- BUTTON DISPLAY OPTIONS METHODS ------- */
	

	public boolean hasSelected() {
        return this.selectedEntityList != null && !this.selectedEntityList.isEmpty();
    }
	
	
	 public String getDeleteButtonMessage() {
	        if (hasSelected()) {
	            int size = this.selectedEntityList.size();
	            return size > 1 ? size + " row selected" : "1 row selected";
	        }

	        return "Delete";
	    }



    /* -------- FILTERING METHOD -----------*/
    
    public void filter() {
        if (filterBean.getSelectedFilterValue() == null || filterBean.getSelectedFilterValue().equals("")) {
          this.entityList = entityService.findAll(entityClass);
          
        } else {
          
            this.entityList = filterBean.filter(entityClass);
            logger.warning("Number of items after filtering: " + String.valueOf(entityList.size()));
        }

        PrimeFaces.current().ajax().update("form:datatable");
    }

   

	
	/* ----- GETTERS AND SETTERS ------*/
	
	public GenericEntityService<T> getEntityService() {
		return entityService;
	}


	public FilterBean<T> getFilterBean() {
		return filterBean;
	}


	public EditRowBean<T> getEditRowBean() {
		return editRowBean;
	}


	public List<T> getEntityList() {
		return entityList;
	}


	public Class<T> getEntityClass() {
		return entityClass;
	}


	public void setEntityService(GenericEntityService<T> entityService) {
		this.entityService = entityService;
	}

	

	public void setFilterBean(FilterBean<T> filterBean) {
		this.filterBean = filterBean;
	}


	public void setEditRowBean(EditRowBean<T> editRowBean) {
		this.editRowBean = editRowBean;
	}


	public void setEntityList(List<T> entityList) {
		this.entityList = entityList;
	}


	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public List<T> getSelectedEntityList() {
		return selectedEntityList;
	}

	public void setSelectedEntityList(List<T> selectedEntityList) {
		this.selectedEntityList = selectedEntityList;
	}





	public String getEntityIdParam() {
		return entityIdParam;
	}



	public void setEntityIdParam(String entityIdParam) {
		this.entityIdParam = entityIdParam;
	}



	public T getEntityInstance() {
		return entityInstance;
	}



	public void setEntityInstance(T entityInstance) {
		this.entityInstance = entityInstance;
	}

}
