package org.route.details.exeption;

public class ApplicationException extends Exception {

	/**
	 * ApplicationException.java long
	 */
	private static final long serialVersionUID = 299042568692057659L;
	private static final String ERR_CODE_INTERNAL_ERR = "500";

	private Error error;
	
	public ApplicationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApplicationException(String message) {
		super(message);
		this.error = new Error(ERR_CODE_INTERNAL_ERR, message);
	}
	
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}
