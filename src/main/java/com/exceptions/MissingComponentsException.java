package com.exceptions;

public class MissingComponentsException  extends Exception{

	private static final long serialVersionUID = 1L;

	public MissingComponentsException(String msg) {
		super(msg);
	}
}
