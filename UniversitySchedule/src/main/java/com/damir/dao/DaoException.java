package com.damir.dao;

public class DaoException extends Exception {

	private static final long serialVersionUID = 4985419357579457921L;

	public DaoException() {
		super();
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
