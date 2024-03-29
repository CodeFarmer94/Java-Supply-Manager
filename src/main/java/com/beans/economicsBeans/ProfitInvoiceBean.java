package com.beans.economicsBeans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import com.entities.Customer;
import com.entities.Product;
import com.entities.ProfitInvoice;
import com.services.GenericEntityService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

public class ProfitInvoiceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /* -------------- Bean instance fields ------------ */
    private Customer customer;

    /* This field will be populated by the user input - Check JSF page */
    private List<Long> invoiceProductIdsList;

    private LocalDate date;

    private double amount;

    /* ------- Service Injections ----------- */


    @Inject
    private GenericEntityService<Product> productService;

    @Inject
    private Logger logger;

    /* ------ Business Logic Methods --------- */

    /* ------ Getters and Setters ------- */

    public Customer getCustomer() {
        return customer;
    }

    public List<Long> getInvoiceProductIdsList() {
        return invoiceProductIdsList;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public GenericEntityService<Product> getProductService() {
        return productService;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setProductService(GenericEntityService<Product> productService) {
        this.productService = productService;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setInvoiceProductIdsList(List<Long> invoiceProductIdsList) {
        this.invoiceProductIdsList = invoiceProductIdsList;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}

