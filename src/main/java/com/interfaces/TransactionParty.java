package com.interfaces;

import com.entities.Address;

public interface TransactionParty {
	
    long getId();
    String getName();
    String getEmail();
    String getContactPhone();
    Address getAddress();
 
}
