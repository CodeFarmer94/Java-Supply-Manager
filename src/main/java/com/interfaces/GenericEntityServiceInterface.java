package com.interfaces;

import java.util.List;

public interface GenericEntityServiceInterface<T> {

	public void save(T entity);
	public T findById(Class<T> entityClass, long id);
	public List<T> findAll(Class<T> entityClass);
	public List<T> findAllSorted(Class<T> entityClass, String columnName, String sortOrder);
	public List<T> findByValue(Class<T> entityClass, String columnName, Object value);
	public List<T> findListById(Class<T> entityClass, List<Long> entityIds);
	public T findLastByColumn(Class<T> entityClass, String columnName);
	public T update(T entity);
	
}
