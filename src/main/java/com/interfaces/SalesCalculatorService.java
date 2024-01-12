package com.interfaces;

import java.io.Serializable;
import java.time.Month;
import java.util.List;
import java.util.Map;

import com.entities.ProfitInvoice;

public abstract class SalesCalculatorService implements Serializable {

	private static final long serialVersionUID = 1L;
	public abstract List<Number> getSalesData(List<ProfitInvoice> invoiceList, int year);
	public abstract List<String> getFields();
	

}
