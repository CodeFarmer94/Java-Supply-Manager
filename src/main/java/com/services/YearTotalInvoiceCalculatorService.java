package com.services;

import java.util.ArrayList;
import java.util.List;

import com.entities.ProfitInvoice;
import com.interfaces.Invoice;
import com.interfaces.InvoiceCalculatorService;


public class YearTotalInvoiceCalculatorService extends InvoiceCalculatorService{

	
	private static final long serialVersionUID = 1L;



	@Override
	public List<String> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Number> getSalesData(List<? extends Invoice> invoiceList, int year) {
		// TODO Auto-generated method stub
		for (Invoice invoice : invoiceList) {
			System.out.println(invoice.getCreatedAt().getYear() + " " + invoice.getTotalAmount());
		}
		List<Number> salesList = new ArrayList<>();
		salesList.add(invoiceList.stream().
				filter(e -> e.getCreatedAt().getYear() == year).
				mapToDouble(e -> e.getTotalAmount()).sum());
		
		
		return salesList;
	}

	
	
}
