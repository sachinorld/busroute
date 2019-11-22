package org.route.details.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TripResponse {

	@JsonProperty
	private String nextDepartureIn;
	@JsonProperty
	private String routeName;
	@JsonProperty
	private String direction;
	@JsonProperty
	private String busStopName;

	public TripResponse(long time, String routeName, String direction, String busStopName) {
		super();
		this.nextDepartureIn = time + " mins";
		this.routeName = routeName;
		this.direction = direction;
		this.busStopName = busStopName;
	}
}
