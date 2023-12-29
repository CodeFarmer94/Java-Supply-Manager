package com.services;

import java.io.Serializable;



import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;

import jakarta.inject.Inject;
import jakarta.inject.Named;



@Named
public class PushContextService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject @Push
	private PushContext someChannel;

	public void sendMessage(Object message) {
	    someChannel.send(message);
	}


	
}
