package org.route.details.exeption;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty
	String code;
	@JsonProperty
	String message;

	Error(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
