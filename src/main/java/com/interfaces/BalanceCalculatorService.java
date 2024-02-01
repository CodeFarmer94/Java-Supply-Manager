package com.interfaces;


import java.io.Serializable;
import java.time.Month;
import java.util.List;
import java.util.Map;

import com.entities.Balance;
import com.entities.ProfitInvoice;

public abstract class BalanceCalculatorService implements Serializable {

	private static final long serialVersionUID = 1L;
	public abstract List<Number> getData(List<? extends Balance> balanceList, int year);



	public List<String> getFields(List<? extends Balance> balanceList, int year) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
