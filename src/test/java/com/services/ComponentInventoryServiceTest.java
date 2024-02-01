package com.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dao.GenericDAO;
import com.entities.Component;
import com.entities.ComponentInventory;
import com.events.BuyComponentEvent;
import com.qualifiers.ComponentInventoryQualifier;

@ExtendWith(MockitoExtension.class)
class ComponentInventoryServiceTest {

    @InjectMocks
    @ComponentInventoryQualifier
    private ComponentInventoryService componentInventoryService;

    @Mock
    private GenericDAO<ComponentInventory> genericDAO;

    @BeforeEach
    void setUp() {
        componentInventoryService.genericDAO = genericDAO;
    }

    @Test
    void testAddNewItem() {
        // Arrange
        List<ComponentInventory> inventoryList = new ArrayList<>();
        Map<Component, Integer> quantityMap = Map.of(new Component("Test Component", 10, "Test Componet"), 1);
        BuyComponentEvent testEvent = new BuyComponentEvent(quantityMap);

        // Stub the DAO to return the inventory list
        when(genericDAO.findAll(ComponentInventory.class)).thenReturn(inventoryList);

        // Act
        componentInventoryService.addItems(testEvent);

        // Verify that the DAO was called with the correct arguments
        verify(genericDAO).save(any(ComponentInventory.class));
        verify(genericDAO).findAll(ComponentInventory.class);
        // Verify the update was not called
        verify(genericDAO, never()).update(any(ComponentInventory.class));
    }

    @Test
    void testAddExistingItem() {
        // Arrange
        Component testComponent = new Component("Test Component", 10, "Test Componet");
        ComponentInventory testInventory = new ComponentInventory(testComponent, 1);
        List<ComponentInventory> inventoryList = new ArrayList<>();
        inventoryList.add(testInventory);

        // Stub the DAO to return the inventory list
        when(genericDAO.findAll(ComponentInventory.class)).thenReturn(inventoryList);

        Map<Component, Integer> quantityMap = Map.of(testComponent, 1);
        BuyComponentEvent testEvent = new BuyComponentEvent(quantityMap);

        // Act
        componentInventoryService.addItems(testEvent);

        // Verify that the DAO was called with the correct arguments
        verify(genericDAO).update(any(ComponentInventory.class));
        verify(genericDAO).findAll(ComponentInventory.class);
        // Verify the save was not called
        verify(genericDAO, never()).save(any(ComponentInventory.class));
    }
}
