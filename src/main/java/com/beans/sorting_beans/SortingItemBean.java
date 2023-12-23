package com.beans.sorting_beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.interfaces.ItemInterface;


import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
public  class SortingItemBean<T extends ItemInterface> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String selectedSortColumn = "id";
    private String selectedSortOrder = "ASC";

    @Inject
    private transient Logger logger;

    public List<T> sortList(List<T> entityList) {
        logger.info("SORTING ITEM LIST...by " + selectedSortColumn + " in " + selectedSortOrder + " order..");
        
        /* Creating a copy of the original list */
        List<T> tempList = new ArrayList<>(entityList);
        /* Declaring a new comparator */
        Comparator<T> cmp;

        switch (selectedSortColumn) {
            case "id": {
                cmp = Comparator.comparing(ItemInterface::getId);
                break;
            }
            case "name": {
                cmp = Comparator.comparing(ItemInterface::getName);
                break;
            }
            case "price": {
                cmp = Comparator.comparing(ItemInterface::getPrice);
                break;
            }
            case "party_id": {
				cmp = Comparator.comparing( e -> e.getTransactionParty().getId());
				break;
			}
            case "party_name": {
				cmp = Comparator.comparing( e -> e.getTransactionParty().getName());
				break;
			}
            // Add more cases based on the properties of the ItemInterface
            default:
                throw new IllegalArgumentException("Unexpected value");
        }

        if (selectedSortOrder.equals("DESC")) {
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

