package com.interfaces;

public interface ItemInterface extends EntityInterface{
	
	long getId();
	String getName();
	double getPrice();
	TransactionParty getTransactionParty();
}
