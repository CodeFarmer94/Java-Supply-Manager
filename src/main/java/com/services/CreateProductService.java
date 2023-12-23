package com.services;

import java.util.Map;
import java.util.logging.Logger;

import com.entities.Component;
import com.entities.Product;
import com.entities.ProductComponent;

import jakarta.ejb.Stateless;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@Stateless
public class CreateProductService {

	
	@Inject 
	private GenericEntityService<Product> productService;
	
	@Inject
	private transient Logger logger;

	@Inject
	private GenericEntityService<ProductComponent> productComponentService;

	
	public void createProductWithComponents(Product product, Map<Component, Integer> quantityMap) {
		logger.info("Saving entity Product...");

		try {

			this.productService.save(product);
			quantityMap.forEach((component, quantity) -> 
					this.productComponentService.save(new ProductComponent(product, component, quantity)));
	      
		} catch (PersistenceException e) {
			logger.warning("PersistenceException occurred: " + e.getMessage());
		} catch (ConstraintViolationException e) {
			logger.warning("Exception during user registration: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
