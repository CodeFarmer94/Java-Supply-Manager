package com.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.entities.Component;
import com.entities.ExpenseInvoice;
import com.entities.Supplier;
import com.events.BuyComponentEvent;


import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Observes;



@Stateless
public class ExpenseInvoiceService extends GenericEntityService<ExpenseInvoice> implements Serializable {

	
	
	private static final long serialVersionUID = 1L;

	public void createInvoicesFromMap(@Observes BuyComponentEvent event) {

		Map<Supplier,List<Component>> map = groupComponentsBySupplier(event.getQuantityMap());
		map.entrySet().forEach(entry -> {
			
			ExpenseInvoice invoice = new ExpenseInvoice(entry.getValue(), entry.getKey());
			System.out.println("Invoice for supplier " + entry.getKey().getName() + " is saved, total amount is " + invoice.getTotalAmount() + "");
			save(invoice);

		});

	}
	
	public Map<Supplier, List<Component>> groupComponentsBySupplier(Map<Component, Integer> quantityMap) {

		Set<Component> list = quantityMap.keySet();
		Map<Supplier, List<Component>> mapSupplierToComponents = list.stream()
				.collect(Collectors.groupingBy(Component::getTransactionParty));
		return mapSupplierToComponents;
		
	}

}
