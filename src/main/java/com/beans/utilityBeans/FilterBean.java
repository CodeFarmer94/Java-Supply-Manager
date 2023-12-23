package com.beans.utilityBeans;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import com.interfaces.EntityInterface;
import com.services.GenericEntityService;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public class FilterBean <T extends EntityInterface> implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String selectedFilterColumn = "id";
	private String selectedFilterValue;
	
	@Inject
	private GenericEntityService<T> genericEntityService;
	 
	 @Inject
	private transient Logger logger;

	 /* TO IMPROVE: FILTERING REQUIRES DB TO BE QUERIED */

	public List<T> filter(Class<T> entityClass){
		
		logger.warning("Filtering column " + selectedFilterColumn + " by value " + selectedFilterValue);
		
		if(selectedFilterColumn.equals("id")) {
			
			logger.warning("Filtered column is ID");
			return genericEntityService.findByValue(entityClass, selectedFilterColumn, Long.valueOf(selectedFilterValue));
		}
		
		return genericEntityService.findByValue(entityClass, selectedFilterColumn, selectedFilterValue);
	}
	
	
	
	
	public void setSelectedFilterColumn(String selectedFilterColumn) {
		this.selectedFilterColumn = selectedFilterColumn;
	}
	public void setSelectedFilterValue(String selectedFilterValue) {
		this.selectedFilterValue = selectedFilterValue;
	}
	
	public String getSelectedFilterColumn() {
		return selectedFilterColumn;
	}
	public String getSelectedFilterValue() {
		return selectedFilterValue;
	}
	
	
}
