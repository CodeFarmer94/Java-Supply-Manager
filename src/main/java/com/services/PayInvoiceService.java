package com.services;

import java.util.Map;

import com.entities.Balance;
import com.entities.Component;
import com.events.BuyComponentEvent;
import com.qualifiers.PaymentEvent;

import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

public class PayInvoiceService {

	@Inject @PaymentEvent
	public Event<Double> paymentEvent;
	
	public void payComponentsTotalCost(@Observes BuyComponentEvent event) {

		double totalCost = getTotalCost(event.getQuantityMap());
		paymentEvent.fire(totalCost);
		System.out.println("Total cost of components is " + totalCost);
		System.out.println("Payment event fired");
	
	}
	public double getTotalCost(Map<Component, Integer> quantityMap) {
		
		return quantityMap.entrySet().stream()
		        .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
		        .sum();

	}

}
