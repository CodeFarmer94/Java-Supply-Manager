package com.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.entities.Component;
import com.entities.ExpenseInvoice;
import com.entities.Supplier;
import com.events.BuyComponentEvent;
import com.qualifiers.ExpenseInvoiceQualifier;

import jakarta.enterprise.event.Observes;


@ExpenseInvoiceQualifier
public class ExpenseInvoiceService extends GenericEntityService<ExpenseInvoice> {

	
	
	public void createInvoicesFromMap(@Observes BuyComponentEvent event) {

		Map<Supplier,List<Component>> map = groupComponentsBySupplier(event.getQuantityMap());
		map.entrySet().forEach(entry -> {
			System.out.println("Invoice for supplier " + entry.getKey().getName() + " is created");
			save(new ExpenseInvoice(entry.getValue(), entry.getKey()));

		});

	}
	
	public Map<Supplier, List<Component>> groupComponentsBySupplier(Map<Component, Integer> quantityMap) {

		Set<Component> list = quantityMap.keySet();
		Map<Supplier, List<Component>> mapSupplierToComponents = list.stream()
				.collect(Collectors.groupingBy(Component::getTransactionParty));
		return mapSupplierToComponents;
		
	}

}
