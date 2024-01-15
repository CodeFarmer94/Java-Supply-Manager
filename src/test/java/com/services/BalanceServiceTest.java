package com.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.entities.Balance;
import com.events.BuyComponentEvent;
import com.qualifiers.PaymentEvent;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private BalanceService balanceService;
   
    @Mock
    private EntityManager em;

   
   @Mock 
	private Event<BuyComponentEvent> buyComponentEvent; 
    @Test
    void testListenPaymentEvent() {
        // Fire the event
		
        buyComponentEvent.fire(new BuyComponentEvent(null));
		// Verify that the event was fired
        	        verify(buyComponentEvent).fire(any());
        	        	
        	        
        

    }
    
    @Test
    void testRemoveFunds() {
   
	Balance balance = balanceService.findLastBalance();
	balanceService.removeFunds(0);
	assertThrows(Exception.class, () ->  balanceService.removeFunds(balance.getBalanceAmount() +10));
	verify(em).persist(any());
    		       
    }
		
    
}
