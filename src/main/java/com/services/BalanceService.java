package com.services;

import java.util.Map;
import java.util.logging.Logger;

import com.entities.Balance;
import com.entities.Component;
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

	}

	public void listenGetPaymentEvent(@Observes @GetPaymentEvent Double amount) {
		addFunds(amount, "Profit: $" + amount);

	}

	/* ------ FIND METHODS ------- */

	public Balance findLastBalance() {

		String columnName = "createdAt";
		Balance lastBalance = this.findLastByColumn(Balance.class, columnName);
		if (lastBalance == null) {
			return new Balance(0, "Initial Balance");
		}
		return lastBalance;

	}

	public void createBalance(double balanceAmount, String transactionType) {

		this.save(new Balance(balanceAmount, transactionType));

	}

	/* ----- ADD / REMOVE METHODS ----- */

	public void addFunds(double fundsToAdd) {

		Balance currentBalance = this.findLastBalance();
		/* Check if no balance is present */
		if (currentBalance == null) {
			this.save(new Balance(fundsToAdd, "Added funds"));
		} else {

			double prevAmount = currentBalance.getBalanceAmount();
			this.save(new Balance(prevAmount + fundsToAdd, "Added funds: $" + fundsToAdd));
		}

	}

	public void addFunds(double fundsToAdd, String description) {

		Balance currentBalance = this.findLastBalance();
		/* Check if no balance is present */
		if (currentBalance == null) {
			this.save(new Balance(fundsToAdd, "Added funds"));
		} else {

			double prevAmount = currentBalance.getBalanceAmount();
			this.save(new Balance(prevAmount + fundsToAdd, description));
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
