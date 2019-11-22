package org.route.details.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude
public class Routes {

	@JsonProperty("Routes")
	private List<Route> routes;

	public Routes() {
		super();
	}

	public Routes(List<Route> routes) {
		super();
		this.routes = routes;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
}
