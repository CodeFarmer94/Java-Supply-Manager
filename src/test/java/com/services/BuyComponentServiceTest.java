package com.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.entities.Address;
import com.entities.Balance;
import com.entities.Component;
import com.entities.Supplier;
import com.events.BuyComponentEvent;

import jakarta.enterprise.event.Event;

@ExtendWith(MockitoExtension.class)
class BuyComponentServiceTest {

    @InjectMocks
    private BuyComponentService buyComponentService;
    
    @Mock
    private BalanceService balanceService;
    
    @Mock
    private Event<BuyComponentEvent> buyComponentEvent;


    @Test
    void testBuyComponentsWithSufficientBalance() {
    	// Arrange
        Supplier testSupplier = new Supplier();
        Map<Component, Integer> componentsToBuy = 
        		Map.of(new Component("Test Component" , 10 ,"Test Componet", testSupplier), 1);
        when(balanceService.findLastBalance()).thenReturn(new Balance(100, "Initial Balance"));
        
        // Act
        buyComponentService.buyComponents(componentsToBuy);
        
        // Assert
        verify(buyComponentEvent).fire(any(BuyComponentEvent.class));
    }
    
    @Test
    void testBuyComponentsWithInsufficientBalance() {
    	// Arrange
    	 Supplier testSupplier = new Supplier();
         Map<Component, Integer> componentsToBuy = 
         		Map.of(new Component("Test Component" , 10 ,"Test Componet", testSupplier), 1);
         when(balanceService.findLastBalance()).thenReturn(new Balance(0, "Initial Balance"));
         
         // Act & Assert
         assertThrows(IllegalStateException.class, () -> buyComponentService.buyComponents(componentsToBuy));
         verify(buyComponentEvent, Mockito.never()).fire(any(BuyComponentEvent.class));
    }
    
    @Test
    void testGetTotalCost() {
        // Arrange
    	Supplier testSupplier = new Supplier();
        Map<Component, Integer> quantityMap = new HashMap<>();
        Component componentA = new Component("ComponentA", 1, "Test Component", testSupplier);
   
        quantityMap.put(componentA, 10);
     

        // Act
        double totalCost = buyComponentService.getTotalCost(quantityMap);

        // Assert
        assertEquals(10, totalCost, 0.001);
    }
    
    @Test
    void testGroupComponentsBySupplier() {
    	// Arrange
    	Supplier testSupplierA = new Supplier("Test Supplier", new Address(), "test email", "test phone" );
    	Supplier testSupplierB = new Supplier("Test Supplier", new Address(), "test email", "test phone" );
    	Component componentA = new Component("test component",10,  "test description", testSupplierA);
    	Component componentB = new Component("test component",10,  "test description", testSupplierB);
    	Map<Component, Integer> componentsToBuy = Map.of(componentA, 1, componentB, 1);
    	
    	// Act
    	Map<Supplier, List<Component>> groupedMap = buyComponentService.groupComponentsBySupplier(componentsToBuy);
    	
    	
    	// Assert
    	assertEquals(groupedMap.get(testSupplierA), List.of(componentA));
    	assertEquals(groupedMap.get(testSupplierB), List.of(componentB));
    }
}
