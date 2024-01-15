package com.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;



public interface  Invoice  extends EntityInterface{

	LocalDateTime getCreatedAt();

	double getTotalAmount();


	
}
