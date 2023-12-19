package com.beans.sorting_beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.interfaces.TransactionParty;

import jakarta.inject.Inject;
import jakarta.inject.Named;


@Named
public class SortingPartyBean<T extends TransactionParty > implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String selectedSortColumn = "id";
	private String selectedSortOrder = "ASC";
	
	 
	@Inject
	private transient Logger logger;
	 
	
		
		public List<T> sortList(List<T> entityList) {
			
		    logger.info("SORTING LIST...by " + selectedSortColumn + " in " + selectedSortOrder + " order..");
		    /* Creating a copy of the original list */
		    List<T> tempList = new ArrayList<>(entityList);
		    /* Declaring a new comparator */
		    Comparator<T> cmp;
		    
		    switch ( selectedSortColumn) {
			case "id": {
				cmp = Comparator.comparing( T::getId);
				break;
			}
			case "name": {
				cmp = Comparator.comparing( T::getName);
				break;
			}
			case "email": {
				cmp = Comparator.comparing( T::getEmail);
				break;
			}
			case "phone": {
				cmp = Comparator.comparing( T::getContactPhone);
				break;
			}
			case "street": {
				cmp = Comparator.comparing( e -> e.getAddress().getStreet());
				break;
			}
			case "zipCode": {
				cmp = Comparator.comparing( e -> e.getAddress().getZipCode());
				break;
			}
			case "country": {
				cmp = Comparator.comparing( e-> e.getAddress().getCountry());
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value" );
			}
		    
		    if(selectedSortOrder.equals("DESC")) {
		    	cmp = cmp.reversed();
		    }
		    tempList.sort(cmp);
		    return tempList;
		   
		}

		
		 public String getSelectedSortColumn() {
				return selectedSortColumn;
			}




			public String getSelectedSortOrder() {
				return selectedSortOrder;
			}




			public void setSelectedSortColumn(String selectedSortColumn) {
				this.selectedSortColumn = selectedSortColumn;
			}




			public void setSelectedSortOrder(String selectedSortOrder) {
				this.selectedSortOrder = selectedSortOrder;
			}

		
	 
}
