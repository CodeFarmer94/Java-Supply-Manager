package com.loggers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Named;



@Named
@ApplicationScoped
public class Logger {

    @Produces
    public java.util.logging.Logger produceLogger(InjectionPoint injectionPoint) {
        String className = injectionPoint.getMember().getDeclaringClass().getName();
        return java.util.logging.Logger.getLogger(className);
    }
}
