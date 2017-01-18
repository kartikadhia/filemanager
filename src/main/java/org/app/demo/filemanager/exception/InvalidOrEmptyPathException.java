package org.app.demo.filemanager.exception;
/**
 * @author Kartik
 * Custom Exception that is thrown when the path is not a valid path in the file system
 */

public class InvalidOrEmptyPathException extends Exception {
	
	public InvalidOrEmptyPathException(String message) {
		super(message);
	}
	@Override
	public String toString() {
		return super.toString();
	}

}
