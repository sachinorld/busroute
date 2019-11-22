package org.route.details.service;

import org.route.details.exeption.ApplicationException;
import org.route.details.exeption.NotFoundException;
import org.route.details.exeption.SystemException;
import org.route.details.model.BusMovement;
import org.route.details.model.TripResponse;

public interface BusRouteService {

	public TripResponse getDepartureTimeFrom(BusMovement busMovement) throws ApplicationException, SystemException, NotFoundException;
}
