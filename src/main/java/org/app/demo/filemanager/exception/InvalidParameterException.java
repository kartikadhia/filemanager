package org.app.demo.filemanager.exception;

/**
 * @author Kartik
 * Custom Exception that is thrown when the any of the parameter is not set properly.
 * 
 */

public class InvalidParameterException extends Exception {

	private static final long serialVersionUID = -3345696770379144066L;
	
	public InvalidParameterException(String message) {
		super(message);
	}
	@Override
	public String toString() {
		return super.toString();
	}

}
