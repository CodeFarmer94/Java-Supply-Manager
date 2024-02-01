package com.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.entities.Balance;
import com.interfaces.BalanceCalculatorService;

public class MonthlyBalanceCalculatorService extends BalanceCalculatorService {

	private static final long serialVersionUID = 1L;


	@Override
	public List<Number> getData(List<? extends Balance> balanceList, int year) {
	
		List<Balance> list = selectedYearBalances(balanceList, year);
		List<Number> numbers = new ArrayList<>();
		list.forEach( b -> numbers.add(b.getBalanceAmount()));
		return numbers;
		
	}

	@Override
	public List<String> getFields(List<? extends Balance> balanceList, int year) {
		List<Balance> list = selectedYearBalances(balanceList, year);
		List<String> strings = new ArrayList<>();
		list.forEach( b -> strings.add(b.getCreatedAt().getMonth().toString() + "-" + b.getCreatedAt().getDayOfMonth()));
		return strings;
	}
	
	
	public List<Balance> selectedYearBalances(List<? extends Balance> balanceList, int year) {
		return balanceList.stream().filter(b -> b.getCreatedAt().getYear() == year).collect(Collectors.toList());
	}

}
