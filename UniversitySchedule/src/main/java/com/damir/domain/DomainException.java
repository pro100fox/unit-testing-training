package com.damir.domain;

public class DomainException extends Exception {

	private static final long serialVersionUID = 5357884295371118428L;
	
	public DomainException() {
		super();
	}

	public DomainException(String message) {
		super(message);
	}

	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}
}
