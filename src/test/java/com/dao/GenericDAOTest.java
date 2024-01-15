package com.dao;

import static org.mockito.Mockito.*;

import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.interfaces.EntityInterface;

import com.validators.ObjValidator;

import jakarta.persistence.EntityManager;
import jakarta.validation.ValidationException;


@ExtendWith(MockitoExtension.class)
class GenericDAOTest {

    @InjectMocks
    private GenericDAO<EntityInterface> genericDAO;

    @Mock
    private EntityManager em;

    @Mock
    private Logger logger;

    @Mock
    private ObjValidator validator;

    @Mock
    private EntityInterface entity;

    
    
    
    @Test
    void testSave() {
        // Initialize mocks
     
        doNothing().when(validator).validateObj(entity);
        genericDAO.save(entity);   
        verify(validator).validateObj(entity);
        verify(em).persist(entity);
      
        verify(logger).info(entity.getClass().getSimpleName() + " saved");
    }
    
    @Test
    void testSaveValidationFailure() {
     
    	doThrow(new ValidationException("Validation failed")).when(validator).validateObj(entity);
        // Verify that the exception is thrown during save
        assertThrows(ValidationException.class, () -> genericDAO.save(entity));

        // Verify that persist and logger methods were not called
        verify(em, never()).persist(entity);
        verify(logger, never()).info(anyString());
    }
    
    @Test
    void testFindById() {
        // Initialize mocks
        when(em.find(EntityInterface.class, 1L)).thenReturn(entity);

        // Call method under test
        genericDAO.findById(EntityInterface.class, 1L);

        // Verify that EntityManager.find() was called with the correct parameters
        verify(em).find(EntityInterface.class, 1L);
        
        
    }
    
}
