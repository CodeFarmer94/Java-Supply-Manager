package com.services;

import java.util.logging.Logger;

import com.entities.Balance;

import jakarta.ejb.Stateless;
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

	/* ------ FIND METHODS ------- */

	public Balance findLastBalance() {
		logger.info("Finding most recent balance...");
		try {
			String columnName = "createdAt";
			return this.findLastByColumn(Balance.class, columnName);
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

	public void removeFunds(double fundsToRemove) {

		try {

			Balance currentBalance = this.findLastBalance();
			if (currentBalance == null) {
				throw new IllegalStateException("Can't remove funds if balance is null");
			}
			double prevAmount = currentBalance.getBalanceAmount();
			if (prevAmount < fundsToRemove) {
				throw new IllegalStateException("Can't remove more funds than the current balance amount");

			}
			this.save(new Balance(prevAmount - fundsToRemove, "Removed  funds: $" + fundsToRemove));

		} catch (IllegalStateException e) {
			logger.warning("Exception during " + this.getClass().getSimpleName() + " operation: " + e.getMessage());

		} catch (ConstraintViolationException e) {
			logger.warning("Exception during " + this.getClass().getSimpleName() + " registration: " + e.getMessage());

		} catch (PersistenceException e) {
			logger.warning("PersistenceException occurred: " + e.getMessage());

		}
	}
}
