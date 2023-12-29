package com.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.primefaces.PrimeFaces;

import jakarta.ejb.Stateless;

@Stateless
public class AsyncProcessService {

	public void startAsyncProcess(Consumer<Integer> remainingTimeCallback,Runnable completionCallback ) {
	    AtomicInteger totalTime = new AtomicInteger(5000); // Total time for the simulation (in milliseconds)
	    int interval = 500;   // Update interval for the remaining time (in milliseconds)

	    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	    executorService.scheduleAtFixedRate(() -> {
	        remainingTimeCallback.accept(totalTime.get());
	        System.out.println("Remaining time: " + totalTime.get());

	        totalTime.addAndGet(-interval);

	        if (totalTime.get() <= 0) {
	            executorService.shutdown(); // Stop the timer when the total time is reached
	            completionCallback.run();
	           

	        }
	    }, 0, interval, TimeUnit.MILLISECONDS);
	}

}
