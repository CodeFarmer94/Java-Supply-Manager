package com.interfaces;

import java.time.LocalDateTime;

import com.entities.Address;

public interface TransactionParty extends EntityInterface{
	
    long getId();
    String getName();
    String getEmail();
    String getContactPhone();
    Address getAddress();
   
 
}
