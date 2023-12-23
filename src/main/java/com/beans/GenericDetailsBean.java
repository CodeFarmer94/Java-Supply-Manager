package com.beans;

import com.interfaces.EntityInterface;
import com.services.GenericEntityService;

import jakarta.inject.Inject;

public class GenericDetailsBean<T extends EntityInterface> {

	
	@Inject 
	private GenericEntityService<T> entityService;
	

	private String entityId;
	private Class<T> entityClass;
	private T entity;
	
	public void fetchEntity() {
		
		this.entity = entityService.findById(entityClass, Long.valueOf(entityId));
		
	}

	
	public GenericEntityService<T> getEntityService() {
		return entityService;
	}

	public String getEntityId() {
		return entityId;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntityService(GenericEntityService<T> entityService) {
		this.entityService = entityService;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
}
