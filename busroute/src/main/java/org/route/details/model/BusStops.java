package org.route.details.model;

import java.util.List;

public class BusStops {

	private List<BusStop> busStops;

	public BusStops() {
		super();
	}

	public BusStops(List<BusStop> busStops) {
		super();
		this.busStops = busStops;
	}

	public List<BusStop> getBusStops() {
		return busStops;
	}

	public void setBusStops(List<BusStop> busStops) {
		this.busStops = busStops;
	}
}
