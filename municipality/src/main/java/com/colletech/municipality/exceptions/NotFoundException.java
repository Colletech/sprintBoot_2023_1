package com.colletech.municipality.exceptions;

public class NotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String message) {
		super("El ID " + message + "no se ha encontrado");
	}
}
