package org.route.details.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude
public class TimepointDeparture {

	@JsonProperty("Actual")
	private String actual;
	@JsonProperty("BlockNumber")
	private String blockNumber;
	@JsonProperty("DepartureText")
	private String departureText;
	@JsonProperty("DepartureTime")
	private String departureTime;
	@JsonProperty("Date")
	private String date;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("Gate")
	private String gate;
	@JsonProperty("Route")
	private String route;
	@JsonProperty("RouteDirection")
	private String routeDirection;
	@JsonProperty("Terminal")
	private String terminal;
	@JsonProperty("VehicleHeading")
	private String vehicleHeading;
	@JsonProperty("VehicleLatitude")
	private String vehicleLatitude;
	@JsonProperty("VehicleLongitude")
	private String vehicleLongitude;
	
	public TimepointDeparture() {
		super();
	}
	
	public TimepointDeparture(String actual, String blockNumber, String departureText, String departureTime,
			String date, String description, String gate, String route, String routeDirection, String terminal,
			String vehicleHeading, String vehicleLatitude, String vehicleLongitude) {
		super();
		this.actual = actual;
		this.blockNumber = blockNumber;
		this.departureText = departureText;
		this.departureTime = departureTime;
		this.date = date;
		this.description = description;
		this.gate = gate;
		this.route = route;
		this.routeDirection = routeDirection;
		this.terminal = terminal;
		this.vehicleHeading = vehicleHeading;
		this.vehicleLatitude = vehicleLatitude;
		this.vehicleLongitude = vehicleLongitude;
	}
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public String getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}
	public String getDepartureText() {
		return departureText;
	}
	public void setDepartureText(String departureText) {
		this.departureText = departureText;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getRouteDirection() {
		return routeDirection;
	}
	public void setRouteDirection(String routeDirection) {
		this.routeDirection = routeDirection;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getVehicleHeading() {
		return vehicleHeading;
	}
	public void setVehicleHeading(String vehicleHeading) {
		this.vehicleHeading = vehicleHeading;
	}
	public String getVehicleLatitude() {
		return vehicleLatitude;
	}
	public void setVehicleLatitude(String vehicleLatitude) {
		this.vehicleLatitude = vehicleLatitude;
	}
	public String getVehicleLongitude() {
		return vehicleLongitude;
	}
	public void setVehicleLongitude(String vehicleLongitude) {
		this.vehicleLongitude = vehicleLongitude;
	}
}
