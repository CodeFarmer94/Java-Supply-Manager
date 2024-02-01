package com.charts;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.entities.ExpenseInvoice;
import com.entities.ProfitInvoice;
import com.interfaces.InvoiceCalculatorService;

import com.interfaces.SerializableFunction;
import com.services.ExpenseInvoiceService;


import com.interceptors.LoggableInterceptor;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.qualifiers.MonthlySalesCalculatorQualifier;
import com.qualifiers.QuarterSalesCalculatorQualifier;

@Named
@SessionScoped
@LoggableInterceptor
public class BarChartComponentPurchases  extends BarChartView {
	
	private static final long serialVersionUID = 1L;
	
	
	@Inject @Named("salesCalculatorStrategy")
	private SerializableFunction<String, InvoiceCalculatorService> salesCalculatorStrategyProducer;
	
		
	@Inject
	private ExpenseInvoiceService expenseInvoiceService;
	
	private String selectedType;
	
	private int selectedYear;

	@PostConstruct
	public void init() {
		selectedType = "monthly";
		selectedYear = 2023;
	}

	@Override
     public List<Number> getChartData() {
    	 List<ExpenseInvoice> purchasesList = expenseInvoiceService.findAll(ExpenseInvoice.class);
    	 return salesCalculatorStrategyProducer.apply(selectedType).getSalesData(purchasesList, selectedYear);
  
     }
     
		public List<String> getFields() {
			return salesCalculatorStrategyProducer.apply(selectedType).getFields();
		}
     
     @Override
		public String getLabel() {
			return "Expenses";
		}

     public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
	
		this.selectedType = selectedType;
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(int selectedYear) {
		this.selectedYear = selectedYear;
	}
     

}
