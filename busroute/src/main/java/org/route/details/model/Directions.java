package org.route.details.model;

public enum Directions {

	NORTH("1"),
	EAST("2"),
	WEST("3"),
	SOUTH("4");
	
	private String direction;
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	Directions(String dir) {
		this.direction = dir;
	}
	
	public static Directions fromString(final String dir) {
		for (Directions di : Directions.values()) {
			if (di.toString().equalsIgnoreCase(dir)) {
				return di;
			}
		}
		return null;
	}
	
}
