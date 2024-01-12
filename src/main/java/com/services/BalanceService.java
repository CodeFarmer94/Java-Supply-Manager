package com.services;

import java.util.logging.Logger;

import com.entities.Balance;
import com.qualifiers.GetPaymentEvent;
import com.qualifiers.PaymentEvent;

import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Observes;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

@Stateless
public class BalanceService extends GenericEntityService<Balance> {

	@Inject
	private transient Logger logger;

	/*------- Business Logic Methods ------ */

	
	
	public void listenPaymentEvent(@Observes @PaymentEvent Double amount) {
        removeFunds(amount, "Payment: $" + amount);
        System.out.println("BalanceService: Payment event received");
        System.out.println("BalanceService: Amount paid is " + amount);
    }
	
	public void listenGetPaymentEvent(@Observes @GetPaymentEvent Double amount) {
        addFunds(amount, "Payment: $" + amount);
        System.out.println("BalanceService: Get Payment event received");
        System.out.println("BalanceService: Amount received is " + amount);
    }
	
	/* ------ FIND METHODS ------- */
	

	public Balance findLastBalance() {
		logger.info("Finding most recent balance...");
		try {
			String columnName = "createdAt";
		 Balance lastBalance = this.findLastByColumn(Balance.class, columnName);
			if (lastBalance == null) {
				return new Balance(0, "Initial Balance");
			}
			
			return lastBalance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void createBalance(double balanceAmount, String transactionType) {
		try {
			this.save(new Balance(balanceAmount, transactionType));

		} catch (ConstraintViolationException e) {
			logger.warning("Exception during " + this.getClass().getSimpleName() + " registration: " + e.getMessage());

		} catch (PersistenceException e) {
			logger.warning("PersistenceException occurred: " + e.getMessage());

		}
	}

	/* ----- ADD / REMOVE METHODS ----- */

	public void addFunds(double fundsToAdd) {

		try {

			Balance currentBalance = this.findLastBalance();
			/* Check if no balance is present */
			if (currentBalance == null) {
				this.save(new Balance(fundsToAdd, "Added funds"));
			} else {

				double prevAmount = currentBalance.getBalanceAmount();
				this.save(new Balance(prevAmount + fundsToAdd, "Added funds: $" + fundsToAdd));
			}

		} catch (ConstraintViolationException e) {
			logger.warning("Exception during " + this.getClass().getSimpleName() + " registration: " + e.getMessage());

		} catch (PersistenceException e) {
			logger.warning("PersistenceException occurred: " + e.getMessage());

		}
	}
	public void addFunds(double fundsToAdd, String description) {

		try {

			Balance currentBalance = this.findLastBalance();
			/* Check if no balance is present */
			if (currentBalance == null) {
				this.save(new Balance(fundsToAdd, "Added funds"));
			} else {

				double prevAmount = currentBalance.getBalanceAmount();
				this.save(new Balance(prevAmount + fundsToAdd, description));
			}

		} catch (ConstraintViolationException e) {
			logger.warning("Exception during " + this.getClass().getSimpleName() + " registration: " + e.getMessage());

		} catch (PersistenceException e) {
			logger.warning("PersistenceException occurred: " + e.getMessage());

		}
	}
	
	

	public void removeFunds(double fundsToRemove) {

	

			Balance currentBalance = this.findLastBalance();
			if (currentBalance == null) {
				throw new IllegalStateException("Can't remove funds if balance is null");
			}
			double prevAmount = currentBalance.getBalanceAmount();
			if (prevAmount < fundsToRemove) {
				throw new IllegalStateException("Can't remove more funds than the current balance amount");

			}
			this.save(new Balance(prevAmount - fundsToRemove, "Removed  funds: $" + fundsToRemove));

	}
	

	public void removeFunds(double fundsToRemove, String description) {

	

			Balance currentBalance = this.findLastBalance();
			if (currentBalance == null) {
				throw new IllegalStateException("Can't remove funds if balance is null");
			}
			double prevAmount = currentBalance.getBalanceAmount();
			if (prevAmount < fundsToRemove) {
				throw new IllegalStateException("Can't remove more funds than the current balance amount");

			}
			this.save(new Balance(prevAmount - fundsToRemove, description));

	}
}
