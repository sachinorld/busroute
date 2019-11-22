package org.route.details.model;

import java.util.List;

public class TimepointDepartures {

	private List<TimepointDeparture> timepointDepartures;

	public TimepointDepartures() {
		super();
	}

	public TimepointDepartures(List<TimepointDeparture> timepointDepartures) {
		super();
		this.timepointDepartures = timepointDepartures;
	}

	public List<TimepointDeparture> getTimepointDepartures() {
		return timepointDepartures;
	}

	public void setTimepointDepartures(List<TimepointDeparture> timepointDepartures) {
		this.timepointDepartures = timepointDepartures;
	}
}
