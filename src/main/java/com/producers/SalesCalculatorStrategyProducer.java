package com.producers;

import java.io.Serializable;
import java.util.function.Function;

import com.interfaces.SerializableFunction;
import com.services.MonthlyInvoiceCalculatorService;

import com.services.QuarterInvoiceCalculatorService;
import com.services.YearTotalInvoiceCalculatorService;
import com.interfaces.InvoiceCalculatorService;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class SalesCalculatorStrategyProducer implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Produces @Named("salesCalculatorStrategy")
	SerializableFunction<String, InvoiceCalculatorService> salesCalculatorStrategyProducer = (String strategy) -> {
		switch (strategy) {
		case "monthly":
			return new MonthlyInvoiceCalculatorService();
		case "quarter":
			return new QuarterInvoiceCalculatorService();
		case "profit/expense":
			return new YearTotalInvoiceCalculatorService();
		default:
			throw new IllegalArgumentException("Invalid strategy: " + strategy);
		}
	};
}
