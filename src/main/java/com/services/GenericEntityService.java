package com.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.dao.GenericDAO;
import com.interfaces.EntityInterface;
import com.interfaces.GenericEntityServiceInterface;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@Stateless
public class GenericEntityService<T extends EntityInterface>  {
	
	
    @Inject
    protected  GenericDAO<T> genericDAO;
    
    

    
    /* -------- POST ------------*/

    public void save( T entity) {
        genericDAO.save(entity);
        
    }
    
    
    /* --------- GET ------------ */ 
    
    public T findById(Class<T> entityClass, long id) {
        return genericDAO.findById(entityClass, id);
    }

    public List<T> findAll(Class<T> entityClass) {
    	return genericDAO.findAll(entityClass);
    }
    
    
    /*  SORTING METHOD */
    public List<T> findAllSorted(Class<T> entityClass, String columnName, String sortOrder){
    	return genericDAO.findAllSorted(entityClass, columnName, sortOrder);
    }
	
    /*  FILTERING METHOD */
    public List<T> findByValue(Class<T> entityClass, String columnName, Object value){
    	return genericDAO.findByValue(entityClass, columnName, value);
    }
  
    
    
    
    public List<T> findListById(Class<T> entityClass, List<Long> entityIds) {
        return entityIds.stream()
                .map(id -> genericDAO.findById(entityClass, id))
                .collect(Collectors.toList());
    }
    
    public T findLastByColumn(Class<T> entityClass, String columnName) {
        try {
            return genericDAO.findLastByColumn(entityClass, columnName);
        } catch (Exception ex) {
       
           ex.printStackTrace();
           return null;
        }
    }
    
    /*------------ PUT -----------*/


	@Transactional
	public T update(T entity) {
        return genericDAO.update(entity);
    }
    
    /* --------- DELETE --------- */
    
    public List<T> removeListById(Class<T> entityClass, List<Long> entityIds) {
        return entityIds.stream()
                .map(id -> genericDAO.delete(entityClass, id))
                .collect(Collectors.toList());
    }
    
    public T remove (Class<T> entityClass, T entity){ 
    	return genericDAO.delete(entityClass, entity.getId());
    }
    
    




    
}
