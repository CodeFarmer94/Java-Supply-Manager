package com.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.interfaces.EntityInterface;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Balance
 *
 */
@Entity

public class Balance implements EntityInterface, Serializable {

	
	private static final long serialVersionUID = 1L;
	
	/* ------ Entity Column fields ------- */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String transactionType;
	
	private String description;
	
	@NotNull
	private double balanceAmount;
	

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

   

	

	
    /* ------- CONSTRUCTORS ------ */

	public Balance() {
		super();
	}
	
	public Balance( @NotNull double balanceAmount, String transactionType) {
		super();
		
		this.balanceAmount = balanceAmount;
		this.transactionType = transactionType;
		this.createdAt = LocalDateTime.now();
	}


	
	
	
	public Balance(@NotNull String transactionType, String description, @NotNull double balanceAmount,
			@NotNull LocalDateTime createdAt) {
		super();
		this.transactionType = transactionType;
		this.description = description;
		this.balanceAmount = balanceAmount;
		this.createdAt = createdAt;
	}


	/*------- Getters and Setters ------*/
    
	public String getTransactionType() {
		return transactionType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public long getId() {
		return id;
	}





	public double getBalanceAmount() {
		return balanceAmount;
	}



	public void setId(long id) {
		this.id = id;
	}





	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}

   
}
