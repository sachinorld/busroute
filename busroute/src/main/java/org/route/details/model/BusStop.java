package org.route.details.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude
public class BusStop {

	@JsonProperty("Text")
	private String text;
	@JsonProperty("Value")
	private String value;
	public BusStop() {
		super();
	}
	public BusStop(String text, String value) {
		super();
		this.text = text;
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
