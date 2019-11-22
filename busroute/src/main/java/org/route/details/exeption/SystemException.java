package org.route.details.exeption;

public class SystemException extends Exception {

	/**
	 * ApplicationException.java long
	 */
	private static final long serialVersionUID = 299042568692057659L;
	private static final String ERR_CODE_GATEWAY_ERR = "502";

	private Error error;
	
	public SystemException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SystemException(String message) {
		super(message);
		this.error = new Error(ERR_CODE_GATEWAY_ERR, message);
	}
	
	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
