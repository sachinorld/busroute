package org.route.details.controller;

import org.route.details.exeption.ApplicationException;
import org.route.details.exeption.NotFoundException;
import org.route.details.exeption.SystemException;
import org.route.details.model.BusMovement;
import org.route.details.model.TripResponse;
import org.route.details.service.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/nexttrip")
public class BusRouteController {

	@Autowired
	private BusRouteService busRouteService;

	@RequestMapping(method = RequestMethod.GET, path = "/check")
	public String healthcheck() {
		return "Ok";
	}

	@GetMapping(path = "/{bus_route}/{direction}/{bus_stop_name}")
	public ResponseEntity<TripResponse> getNextBusDepartureTime(@PathVariable("bus_route") String busRouteDesc,
			@PathVariable("direction") String direction, @PathVariable("bus_stop_name") String busStopName) throws ApplicationException, SystemException, NotFoundException {
		TripResponse nextDeptTime = null;

		System.out.println("bus_route" + busRouteDesc + "; direction" + direction + "; bus_stop_name" + busStopName);

		BusMovement busMovement = new BusMovement(busRouteDesc, direction, busStopName);
		nextDeptTime = busRouteService.getDepartureTimeFrom(busMovement);

		return ResponseEntity.status(HttpStatus.OK).header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
				.body(nextDeptTime);
	}
}
