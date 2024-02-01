package com.charts;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.entities.ExpenseInvoice;
import com.entities.ProfitInvoice;
import com.interfaces.InvoiceCalculatorService;
import com.interfaces.SerializableFunction;
import com.services.ExpenseInvoiceService;

import com.services.ProfitInvoiceService;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ViewScoped
@Named
public class DonutChartExpenseProfit extends DonutChart {

	private static final long serialVersionUID = 1L;
	
	@Inject @Named("salesCalculatorStrategy")
	private SerializableFunction<String, InvoiceCalculatorService> salesCalculatorStrategyProducer;
	
	@Inject
	private ProfitInvoiceService profitInvoiceService;
	
	@Inject
	private ExpenseInvoiceService expenseInvoiceService;
	
	
	@Override
	public List<Number> getDataSet(String type, int year) {
		
		List<ProfitInvoice> salesList = profitInvoiceService.findAll(ProfitInvoice.class);
		List<ExpenseInvoice> purchasesList = expenseInvoiceService.findAll(ExpenseInvoice.class);
		List<Number> totalSales  = salesCalculatorStrategyProducer.apply(type).getSalesData(salesList, year);
		
		List<Number> totalPurchases  = salesCalculatorStrategyProducer.apply(type).getSalesData(purchasesList, year);
		// Concate the lists
		List<Number> totalSalesPurchases = new ArrayList<>();
		totalSalesPurchases.addAll(totalSales);
		totalSalesPurchases.addAll(totalPurchases);
		System.out.println("Total Sales Purchases: " + totalSalesPurchases + "Total Sales: " + totalSales + "Total Purchases: " + totalPurchases);
		return totalSalesPurchases;
		
	}

	@Override
	public List<String> getFields(String type) {
		// TODO Auto-generated method stub
		return List.of("Sales", "Purchases");
	}

	
	
}
