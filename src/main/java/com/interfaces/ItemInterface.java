package com.interfaces;

public interface ItemInterface {
	
	long getId();
	String getName();
	double getPrice();
	int getQuantity();
	TransactionParty getTransactionParty();
}
