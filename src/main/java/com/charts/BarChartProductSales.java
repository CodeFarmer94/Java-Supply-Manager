package com.charts;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.entities.ProfitInvoice;
import com.interfaces.InvoiceCalculatorService;

import com.interfaces.SerializableFunction;
import com.services.MonthlySalesCalculatorService;
import com.services.ProfitInvoiceService;

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
public class BarChartProductSales  extends BarChartView {
	
	private static final long serialVersionUID = 1L;
	
	
	@Inject @Named("salesCalculatorStrategy")
	private SerializableFunction<String, InvoiceCalculatorService> salesCalculatorStrategyProducer;
	
		
	@Inject
	private ProfitInvoiceService profitInvoiceService;
	
	private String selectedType;

	@PostConstruct
	public void init() {
		selectedType = "monthly";
	}

	@Override
     public List<Number> getChartData(String type, int year) {
    	 List<ProfitInvoice> salesList = profitInvoiceService.findAll(ProfitInvoice.class);
    	 return salesCalculatorStrategyProducer.apply(type).getSalesData(salesList, year);
  
     }
     
		public List<String> getFields(String type) {
			return salesCalculatorStrategyProducer.apply(type).getFields();
		}
     
     @Override
		public String getLabel() {
			return "Product Sales";
		}

     public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
	
		this.selectedType = selectedType;
	}
     

}
