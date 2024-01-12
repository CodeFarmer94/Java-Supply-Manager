package com.services;

import java.util.ArrayList;
import java.util.List;

import com.entities.ProfitInvoice;
import com.interfaces.SalesCalculatorService;

public class YearSalesCalculatorService extends SalesCalculatorService{

	
	public List<Number> getSalesData(List<ProfitInvoice> invoiceList, int year) {

		List<Number> salesList = new ArrayList<>();
		salesList.add(invoiceList.stream().
				filter(e -> e.getCreatedAt().getYear() == year).
				mapToDouble(e -> e.getTotalAmount()).sum());
		return salesList;
	}

	@Override
	public List<String> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
