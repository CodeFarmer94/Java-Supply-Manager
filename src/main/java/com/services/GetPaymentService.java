package com.services;

import java.util.Map;

import com.entities.Component;
import com.entities.Product;
import com.qualifiers.BuyComponentEvent;
import com.qualifiers.GetPaymentEvent;
import com.qualifiers.PaymentEvent;
import com.qualifiers.SellProductEvent;

import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

public class GetPaymentService {
	
	
	@Inject @GetPaymentEvent
	public Event<Double> getPaymentEvent;
	
	public void payComponentsTotalCost(@Observes @SellProductEvent Map<Product, Integer> quantityMap) {

		double totalCost = getTotalCost(quantityMap);
		getPaymentEvent.fire(totalCost);
		System.out.println("Total cost of components is " + totalCost);
		System.out.println("Payment event fired");
	
	}
	public double getTotalCost(Map<Product, Integer> quantityMap) {
		
		return quantityMap.entrySet().stream()
		        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
		        .sum();

	}

}
