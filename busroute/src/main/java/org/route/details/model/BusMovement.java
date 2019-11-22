package org.route.details.model;

@SuppressWarnings("unused")
public class BusMovement {

	private String busRouteId;
	private String busRouteName;
	private String direction;
	private String busStopName;
	public BusMovement() {
		super();
	}
	public BusMovement(String busRouteDesc, String direction, String busStopName) {
		super();
		this.busRouteName = busRouteDesc;
		this.direction = direction;
		this.busStopName = busStopName;
	}
	public BusMovement(String busRouteId, String busRouteDesc, String direction, String busStopName) {
		super();
		this.busRouteId = busRouteId;
		this.busRouteName = busRouteDesc;
		this.direction = direction;
		this.busStopName = busStopName;
	}
	public String getBusRouteId() {
		return busRouteId;
	}
	public String getBusRouteName() {
		return busRouteName;
	}
	public String getDirection() {
		return direction;
	}
	public String getBusStopName() {
		return busStopName;
	}
}
