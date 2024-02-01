package com.producers;

import java.io.Serializable;

import com.interfaces.BalanceCalculatorService;
import com.interfaces.SerializableFunction;
import com.services.MonthlyBalanceCalculatorService;


import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class BalanceCalculatorStrategyProducer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 @Produces @Named("balanceCalculatorStrategy")
	 SerializableFunction<String, BalanceCalculatorService> balanceCalculatorStrategyProducer = (String strategy) -> {
		             switch (strategy) {
            case "monthly":
                return new MonthlyBalanceCalculatorService();
           
            default:
                throw new IllegalArgumentException("Invalid strategy: " + strategy);
            }
        };
	 }


