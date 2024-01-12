package com.dao;

import java.util.List;
import java.util.logging.Logger;

import com.interfaces.EntityInterface;
import com.validators.ObjValidator;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * Generic Data Access Object (DAO) for performing CRUD operations on entities.
 *
 * @param <T> The type of entity managed by the DAO.
 */
@Stateless
public class GenericDAO<T extends EntityInterface> {


	@PersistenceContext
    private EntityManager em;

    @Inject
    private Logger logger;

    @Inject
    private ObjValidator validator;

    /**
     * Saves the given entity to the database.
     *
     */
    public void save(T entity) {
    	
    	logger.info("Saving entity ID: " + entity.getId());
        validator.validateObj(entity);
        em.persist(entity);
        logger.info(entity.getClass().getSimpleName() + " saved");
    }

    /**
     * Finds and retrieves an entity by its ID.

     */
    public T findById(Class<T> entityClass, long id) {
        T entity = em.find(entityClass, id);

        if (entity == null) {
            throw new EntityNotFoundException(
                    entityClass.getSimpleName() + " with id " + id + " not found");
        }

        return entity;
    }

    /**
     * Retrieves all entities of a given class from the database.

     */
    public List<T> findAll(Class<T> entityClass) {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    /**
     * Retrieves all entities of a given class from the database and sorts them based on the specified column and order.
     *
  
     */
    public List<T> findAllSorted(Class<T> entityClass, String columnName, String sortOrder) {
    	
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";

        if (columnName != null && !columnName.isEmpty()) {
            queryString += " ORDER BY e." + columnName + (sortOrder == "ASC" ? " ASC" : " DESC");
        }

        return em.createQuery(queryString, entityClass).getResultList();
    }

    /**
     * Retrieves entities of a given class from the database based on a specified column and value.
     *
 
     */
    public List<T> findByValue(Class<T> entityClass, String columnName, Object value) {
    	
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        if (columnName != null && !columnName.isEmpty() && value != null) {
            queryString += " WHERE LOWER(e." + columnName + ") LIKE :value";
        }
        TypedQuery<T> query = em.createQuery(queryString, entityClass);
        if (value != null) {
            query.setParameter("value", "%" + value.toString().toLowerCase() + "%");
        }

        return query.getResultList();
    }
    
    public T findLastByColumn(Class<T> entityClass, String columnName) {
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e";

        if (columnName != null && !columnName.isEmpty()) {
            queryString += " ORDER BY e." + columnName + " DESC";
        }

        TypedQuery<T> query = em.createQuery(queryString, entityClass);

        query.setMaxResults(1); 

        List<T> resultList = query.getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }


    /**
     * Deletes an entity from the database based on its ID.

     */
    public T delete(Class<T> entityClass, long id) {
        T entity = findById(entityClass, id);
        if (entity == null) {
            throw new EntityNotFoundException(
                    entityClass.getSimpleName() + " with id " + id + " not found");
        }
        em.remove(entity);
        logger.info(entityClass.getSimpleName() + " with ID:" + id + " deleted");
        return entity;
    }


    /**
     * Updates the given entity in the database.
     *
     * @param entity The entity to be updated.
     * @return The updated entity.
     */
    public T update(T entity) {
        validator.validateObj(entity);
        T managedEntity = em.merge(entity);
        logger.info(entity.getClass().getSimpleName() + " updated");
        return managedEntity;
    }
    
 
}

