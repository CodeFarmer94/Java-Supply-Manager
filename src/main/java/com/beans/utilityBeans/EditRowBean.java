package com.beans.utilityBeans;

import org.primefaces.event.RowEditEvent;


import com.services.GenericEntityService;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.logging.Logger;


@Named
public class EditRowBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Logger logger;
    
    @Inject 
    private GenericEntityService<T> genericEntityService;


 
    @Transactional
    public void onRowEdit(RowEditEvent<T> event) {
	   	logger.info("processing edit...");
	   	try {
	   		   	T modifiedObj = event.getObject();
	   	genericEntityService.update(modifiedObj);
	   	
        FacesMessage msg = new FacesMessage(modifiedObj.getClass().getSimpleName() + " Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
	   	} catch ( Exception e) {
	   		e.printStackTrace();
;	   	}
	
    }

   
    public void onRowCancel(RowEditEvent<T> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }



	public GenericEntityService<T> getGenericEntityService() {
		return genericEntityService;
	}





	public void setGenericEntityService(GenericEntityService<T> genericEntityService) {
		this.genericEntityService = genericEntityService;
	}



	
    
  

    
}
