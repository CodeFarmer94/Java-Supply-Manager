package com.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.entities.ProfitInvoice;
import com.interfaces.SalesCalculatorService;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.qualifiers.QuarterSalesCalculatorQualifier;

@Stateless
public class QuarterSalesCalculatorService extends SalesCalculatorService {
	
	
	private static final long serialVersionUID = 1L;
	
	public enum SalesChartFields {
		FIRST_QUARTER,
		SECOND_QUARTER,
		THIRD_QUARTER,
		FOURTH_QUARTER
		
	}
	
	
	@Transactional
	public List<Number> getSalesData(List<ProfitInvoice> totalInvoiceList){
		
		List<Number> salesList = new ArrayList<>();
		Map<Month, List<ProfitInvoice>> salesMap  = totalInvoiceList.stream().collect(Collectors.groupingBy( e -> e.getCreatedAt().getMonth().firstMonthOfQuarter()));
		
		salesMap.keySet().forEach(e -> {
			List<ProfitInvoice> invoicePerMonthList = salesMap.get(e);
			
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
	
	@Transactional
	public List<Number> getSalesData(List<ProfitInvoice> totalInvoiceList , int year){
		
		List<Number> salesList = new ArrayList<>();
		Map<Month, List<ProfitInvoice>> salesMap  = totalInvoiceList.stream()
				.filter(e -> e.getCreatedAt().getYear() == year)
				.collect(Collectors.groupingBy( e -> e.getCreatedAt().getMonth().firstMonthOfQuarter()));
		
		salesMap.keySet().forEach(e -> {
			List<ProfitInvoice> invoicePerMonthList = salesMap.get(e);
			
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
	
	
	public List<String> getFields() {
		List<String> fields = new ArrayList<>();
		for (SalesChartFields field : SalesChartFields.values()) {
			fields.add(field.toString());
		}
		return fields;
	}
}
