package com.producers;

import java.io.Serializable;
import java.util.function.Function;

import com.interfaces.SerializableFunction;
import com.services.MonthlySalesCalculatorService;
import com.services.QuarterSalesCalculatorService;
import com.services.YearPurchasesSalesCalculatorService;
import com.interfaces.InvoiceCalculatorService;



import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class SalesCalculatorStrategyProducer implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Produces @Named("salesCalculatorStrategy")
	SerializableFunction<String, InvoiceCalculatorService> salesCalculatorStrategyProducer = (String strategy) -> {
		switch (strategy) {
		case "monthly":
			return new MonthlySalesCalculatorService();
		case "quarter":
			return new QuarterSalesCalculatorService();
		case "profit/expense":
			return new YearPurchasesSalesCalculatorService();
		default:
			throw new IllegalArgumentException("Invalid strategy: " + strategy);
		}
	};
}
