package com.beans.economicsBeans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.primefaces.PrimeFaces;

import com.beans.GenericQuantityMap;
import com.entities.Component;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class BuyOrderBean  implements Serializable{
	
	
	@Inject
	private transient Logger logger;
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GenericQuantityMap<Component> quantityMapService;
	
	@PostConstruct
	public void init() {
		quantityMapService.setQuantityMap(new HashMap<Component, Integer>());
	}

	public GenericQuantityMap<Component> getQuantityMapService() {
		return quantityMapService;
	}

	public void setQuantityMapService(GenericQuantityMap<Component> quantityMapService) {
		this.quantityMapService = quantityMapService;
	}


	
}
