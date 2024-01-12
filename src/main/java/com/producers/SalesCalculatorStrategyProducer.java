package com.producers;

import java.io.Serializable;
import java.util.function.Function;

import com.interfaces.SerializableFunction;
import com.services.MonthlySalesCalculatorService;
import com.services.QuarterSalesCalculatorService;
import com.interfaces.SalesCalculatorService;


import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class SalesCalculatorStrategyProducer implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Produces @Named("salesCalculatorStrategy")
	SerializableFunction<String, SalesCalculatorService> salesCalculatorStrategyProducer = (String strategy) -> {
		switch (strategy) {
		case "monthly":
			return new MonthlySalesCalculatorService();
		case "quarter":
			return new QuarterSalesCalculatorService();
		default:
			throw new IllegalArgumentException("Invalid strategy: " + strategy);
		}
	};
}
