package com.services;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.entities.Balance;
import com.entities.ProfitInvoice;
import com.interfaces.Invoice;
import com.interfaces.InvoiceCalculatorService;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.qualifiers.MonthlySalesCalculatorQualifier;

@Stateless
@MonthlySalesCalculatorQualifier 
public class  MonthlySalesCalculatorService extends InvoiceCalculatorService{
	

	private static final long serialVersionUID = 1L;
	
	public enum SalesChartFields {
		JANUARY,
		FEBRUARY,
		MARCH,
		APRIL,
		MAY,
		JUNE,
		JULY,
		AUGUST,
		SEPTEMBER,
		OCTOBER,
		NOVEMBER,
		DECEMBER
		
	}
	
	

	@Transactional
	public List<Number> getSalesData(List<? extends Invoice> invoiceList, int year){
		
		List<Number> salesList = new ArrayList<>();
		Map<Month, List<Invoice>> salesMap  = invoiceList.stream()
				.filter(e -> e.getCreatedAt().getYear() == year)
				.collect(Collectors.groupingBy( e -> e.getCreatedAt().getMonth()));
		
		salesMap.keySet().forEach(e -> {
			List<Invoice> invoicePerMonthList = salesMap.get(e);
			double totalValue = invoicePerMonthList
					.stream()
					.mapToDouble(invoice -> {
						return invoice.getTotalAmount();
					})
					.sum();
			salesList.add(Double.valueOf(totalValue));
			
		});
		return salesList;
	}
	

	public List<String> getFields(){
		List<String> fields = new ArrayList<>();
		for (SalesChartFields field : SalesChartFields.values()) {
			fields.add(field.toString());
		}
		return fields;
	}

	
	


}
