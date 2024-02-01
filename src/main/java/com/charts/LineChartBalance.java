package com.charts;


import java.util.List;

import com.entities.Balance;
import com.entities.ExpenseInvoice;
import com.entities.ProfitInvoice;
import com.interfaces.BalanceCalculatorService;
import com.interfaces.InvoiceCalculatorService;
import com.interfaces.SerializableFunction;
import com.services.BalanceService;
import com.services.ExpenseInvoiceService;
import com.services.ProfitInvoiceService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class LineChartBalance extends LineChart {

	private static final long serialVersionUID = 1L;
	
	@Inject @Named("balanceCalculatorStrategy")
	private SerializableFunction<String, BalanceCalculatorService> balanceCalculatorStrategyProducer;

	@Inject
	private BalanceService balanceService;
	
	private String selectedType;
	private int selectedYear;
	
	@PostConstruct
	public void init() {
		selectedType = "monthly";
		selectedYear = 2024;
		
	}
	
	@Override
	public List<Number> getChartData() {
		List<Balance> balanceList = balanceService.findAll(Balance.class);
		return balanceCalculatorStrategyProducer.apply(selectedType).getData(balanceList, selectedYear);
	}


	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getFields() {
		List<Balance> balanceList = balanceService.findAll(Balance.class);
		return balanceCalculatorStrategyProducer.apply(selectedType).getFields(balanceList, selectedYear);
	}
	
	

	
}
