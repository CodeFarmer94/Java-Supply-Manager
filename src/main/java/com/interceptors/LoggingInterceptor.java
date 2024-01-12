package com.interceptors;



import java.io.Serializable;
import java.util.logging.Logger;

import jakarta.inject.Inject;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;



@LoggableInterceptorBinding
@Interceptor
public class LoggingInterceptor implements Serializable{

	
		
		private static final long serialVersionUID = 1L;

		@AroundInvoke
		private Object intercept(InvocationContext context) throws Exception {

			System.out.println("Entering method: " + context.getMethod().getName() + " in class: "
					+ context.getTarget().getClass().getName());

			return context.proceed();
		}
}
