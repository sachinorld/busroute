package org.route.details.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude
public class Route {

	@JsonProperty("Description")
	private String description;
	@JsonProperty("ProviderID")
	private String providerId;
	@JsonProperty("Route")
	private String routeId;
	
	public Route(String description, String providerId, String routeId) {
		super();
		this.description = description;
		this.providerId = providerId;
		this.routeId = routeId;
	}
	public Route() {
		super();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
}
