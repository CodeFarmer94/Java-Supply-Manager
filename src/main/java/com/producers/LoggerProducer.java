package com.producers;




import org.slf4j.Logger;

import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Named;
import jakarta.ws.rs.Produces;


public class LoggerProducer {

	/*
	 * Not working - The container is not able to inject the logger
	 */
	@Produces @Named("logger")
	private Logger produceLogger(InjectionPoint ip) {
	    return org.slf4j.LoggerFactory.getLogger(ip.getMember().getDeclaringClass().getName());
	}

	
}
