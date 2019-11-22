package org.route.details.exeption;

public class NotFoundException extends Exception {

	/**
	 * NotFoundException.java long
	 */
	private static final long serialVersionUID = -937785553439765430L;
	private static final String ERR_CODE_NOT_FOUND = "404";

	private Error error;
	
	public NotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotFoundException(String message) {
		super(message);
		this.error = new Error(ERR_CODE_NOT_FOUND, message);
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
