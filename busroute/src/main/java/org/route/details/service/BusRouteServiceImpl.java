package org.route.details.service;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TimeZone;

import org.route.details.constants.Constants;
import org.route.details.exeption.ApplicationException;
import org.route.details.exeption.NotFoundException;
import org.route.details.exeption.SystemException;
import org.route.details.model.BusMovement;
import org.route.details.model.BusStop;
import org.route.details.model.Directions;
import org.route.details.model.Route;
import org.route.details.model.TimepointDeparture;
import org.route.details.model.TripResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BusRouteServiceImpl implements BusRouteService {

	private static final Logger log = LoggerFactory.getLogger(BusRouteServiceImpl.class);
	
	@Value("${ext.api.metrotransit.loc}")
	private String metroTransitLoc;

	@Autowired
	@Qualifier("nextripRestTemplate")
	private RestTemplate restTemplate;

	ObjectMapper mapper = new ObjectMapper();

	/**
	 * returns time until next departure from given bus stop on a given route and
	 * direction.
	 */
	@Override
	public TripResponse getDepartureTimeFrom(BusMovement busMovement) throws ApplicationException, SystemException, NotFoundException {

		TimepointDeparture departure;
		try {
			// get the routeId based on bus route description
			Route busRoute = getBusRoute(busMovement.getBusRouteName());

// use busrouteId fetched, direction and busStop provided to get the stop in that route+direction
			BusStop busStop = getBusStopInRoute(busRoute.getRouteId(), busMovement.getDirection(),
					busMovement.getBusStopName());

			// get departure details of the first bus at Stop X in Route Y in Direction Z
			departure = getDepartureDetails(busRoute.getRouteId(), Directions.fromString(busMovement.getDirection()), busStop.getValue());
		} catch (NotFoundException e) {
			throw new NotFoundException(MessageFormat.format(Constants.ERR_NOT_FOUND_MSG, e.getMessage()));
		} catch (ApplicationException e) {
			throw new ApplicationException(MessageFormat.format(Constants.ERR_APP_INTERNAL_ERR, e.getMessage()));
		} catch (SystemException e) {
			throw new SystemException(MessageFormat.format(Constants.ERR_GATEWAY_ERR, e.getMessage()));
		}
		long timeUntil = timeUntilBusDeparts(departure);
		return new TripResponse(timeUntil, busMovement.getBusRouteName(), busMovement.getDirection(), busMovement.getBusStopName());
	}

	/**
	 * Calculates time until next bus departure 
	 * 
	 * @param departure
	 * @return
	 * @throws ApplicationException 
	 */
	private long timeUntilBusDeparts(TimepointDeparture departure) throws ApplicationException {
		String deptTime = departure.getDepartureTime();
		long mins;
		try {
			int index1 = deptTime.indexOf("(") + 1;
			int index2 = deptTime.indexOf("-");
			
			String timeInMS = String.join("", deptTime.subSequence(index1, index2));
			System.out.println("deptTime:" + timeInMS);
			LocalDateTime now = LocalDateTime.now(TimeZone.getDefault().toZoneId());
			long deptTimeLong = Long.parseLong(timeInMS);
			LocalDateTime then = Instant.ofEpochMilli(deptTimeLong).atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			mins = now.until(then, ChronoUnit.MINUTES);
			return mins;
		} catch (NumberFormatException e) {
			log.error("NumberFormatException at timeUntilBusDeparts. " + e.getMessage());
			throw new ApplicationException(MessageFormat.format(Constants.ERR_APP_INTERNAL_ERR, Constants.CTX_GET_BUS_STOP_DEPT_DETAILS));
		}
	}

	public Route getBusRoute(String routeName) throws ApplicationException, SystemException, NotFoundException {
		String routes = null;
		String url = location(Constants.ROUTES);
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.KEY_ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		ResponseEntity<String> routeResponse;
		try {
			routeResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (RestClientException e) {
			log.error("RestClientException at getBusRoute. " + e.getMessage());
			throw new SystemException(Constants.CTX_GET_BUS_ROUTE);
		}

		switch (routeResponse.getStatusCodeValue()) {
		case 200:
			routes = routeResponse.getBody();
			break;
		default:
			handleError(routeResponse.getStatusCodeValue(), Constants.CTX_GET_BUS_ROUTE);
		}

		List<Route> rData = null;
		try {
			rData = mapper.readValue(routes, new TypeReference<List<Route>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException at getBusRoute. " + e.getMessage());
			throw new ApplicationException(Constants.CTX_GET_BUS_ROUTE);
		}

		if (rData == null || rData.isEmpty()) {
			log.error("Bus Route api result is null");
			throw new NotFoundException(Constants.CTX_GET_BUS_ROUTE);
		}
		Route result = null;
		
		for (Route route : rData) {
			if (!routeName.equalsIgnoreCase(route.getDescription())) {
				continue;
			} else {
				result = route;
				break;
			}
		}
		
		if (result == null) {
			throw new NotFoundException(Constants.CTX_GET_BUS_ROUTE);
		}
		return result;
	}

	public BusStop getBusStopInRoute(String routeId, String direction, String busStopName)
			throws ApplicationException, SystemException, NotFoundException {
		Directions dir = Directions.fromString(direction);
		if (dir == null) {
			log.error("Invalid input direction" + direction);
			throw new IllegalArgumentException("Invalid input Direction = " + direction);
		}
		String url = location(Constants.BUS_STOPS, routeId, dir.getDirection());

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.KEY_ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		ResponseEntity<String> busStopsResponse;
		try {
			busStopsResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (RestClientException e) {
			log.error("RestClientException at getBusStopInRoute. " + e.getMessage());
			throw new SystemException(Constants.CTX_GET_BUS_STOP);
		}
		String stops = null;

		switch (busStopsResponse.getStatusCodeValue()) {
		case 200:
			stops = busStopsResponse.getBody();
			break;
		default:
			handleError(busStopsResponse.getStatusCodeValue(), Constants.CTX_GET_BUS_STOP);
		}

		List<BusStop> sData = null;
		try {
			sData = mapper.readValue(stops, new TypeReference<List<BusStop>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException at getBusStopInRoute. " + e.getMessage());
			throw new ApplicationException(Constants.CTX_GET_BUS_STOP);
		}

		if (sData == null || sData.isEmpty()) {			
			log.error("Bus Stops result is null");
			throw new NotFoundException(Constants.CTX_GET_BUS_STOP);
		}
		BusStop result = null;
		for (BusStop busStop : sData) {
			if (!busStopName.equalsIgnoreCase(busStop.getText())) {
				continue;
			} else {
				result = busStop;
				break;
			}
		}
		if (result == null) {
			throw new NotFoundException(Constants.CTX_GET_BUS_STOP);
		}
		return result;
	}

	public TimepointDeparture getDepartureDetails(String routeId, Directions direction, String busStopId)
			throws ApplicationException, SystemException, NotFoundException {
		String url = location(Constants.DEPARTURE_DETAILS, routeId, direction.getDirection(), busStopId);

		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.KEY_ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		ResponseEntity<String> deptDetailResponse;
		try {
			deptDetailResponse = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		} catch (RestClientException e) {
			log.error("RestClientException at getDepartureDetails. " + e.getMessage());
			throw new SystemException(Constants.CTX_GET_BUS_STOP_DEPT_DETAILS);
		}
		String departures = null;

		switch (deptDetailResponse.getStatusCodeValue()) {
		case 200:
			departures = deptDetailResponse.getBody();
			break;
		default:
			handleError(deptDetailResponse.getStatusCodeValue(), Constants.CTX_GET_BUS_STOP_DEPT_DETAILS);
		}

		List<TimepointDeparture> dData = null;
		try {
			dData = mapper.readValue(departures, new TypeReference<List<TimepointDeparture>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException at getDepartureDetails. " + e.getMessage());
			throw new ApplicationException(Constants.CTX_GET_BUS_STOP_DEPT_DETAILS);
		}

		if (dData == null || dData.isEmpty()) {
			log.error("Bus Departure Details api result is null");
			throw new NotFoundException(Constants.CTX_GET_BUS_STOP_DEPT_DETAILS);
		}
		return dData.get(0);
	}

	/**
	 * handles back end api error other than 200
	 * 
	 * @param statusCode
	 * @param context
	 * @throws SystemException
	 */
	private void handleError(int statusCode, String context) throws SystemException {
		throw new SystemException(MessageFormat.format(Constants.ERR_GATEWAY_ERR, context));
	}

	/**
	 * prepares back end api url with path params
	 * 
	 * @param api
	 * @param pathParams
	 * @return
	 */
	private String location(String api, String... pathParams) {
		String location = metroTransitLoc + api;
		for (int i = 0; i < pathParams.length; i++) {
			location += Constants.KEY_SLASH + pathParams[i];
		}
		return location;
	}

	/**
	 * prepares back end api url
	 * 
	 * @param api
	 * @return
	 */
	private String location(String api) {
		return metroTransitLoc + api;
	}
}
