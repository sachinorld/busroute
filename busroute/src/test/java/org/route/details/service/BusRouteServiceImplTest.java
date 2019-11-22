package org.route.details.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.route.details.BusrouteApplication;
import org.route.details.exeption.ApplicationException;
import org.route.details.exeption.NotFoundException;
import org.route.details.exeption.SystemException;
import org.route.details.model.BusMovement;
import org.route.details.model.BusStop;
import org.route.details.model.Directions;
import org.route.details.model.Route;
import org.route.details.model.TimepointDeparture;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BusrouteApplication.class)
class BusRouteServiceImplTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private RestTemplate restTemplate;

	@InjectMocks
	private BusRouteServiceImpl service;

	@Before
	public void before() {
		service = new BusRouteServiceImpl();
	}

	@Test
	void testGetBusRoute() {
		try {
			String body = "[{\"Description\":\"Metro Blue Line\", \"Route\":\"901\", \"ProviderID\":\"8\"}]";
			ResponseEntity<String> response = Mockito.spy(ResponseEntity.status(HttpStatus.OK)).body(body);
			Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
					Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenReturn(response);

			Route route = service.getBusRoute("Metro Blue Line");
			assertEquals("8", route.getProviderId());
		} catch (ApplicationException | SystemException | NotFoundException e) {
			fail("Exception at testGetBusRoute: " + e.getLocalizedMessage());
		}
	}

	@Test
	void testGetBusStop() {
		try {
			String body = "[{\"Text\":\"Target Field Station Platform 2\",\"Value\":\"TF22\"}]";
			ResponseEntity<String> response = Mockito.spy(ResponseEntity.status(HttpStatus.OK)).body(body);
			Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
					Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenReturn(response);

			BusStop stop = service.getBusStopInRoute("901", "South", "Target Field Station Platform 2");
			assertEquals("TF22", stop.getValue());
		} catch (ApplicationException | SystemException | NotFoundException e) {
			fail("Exception at testGetBusRoute: " + e.getLocalizedMessage());
		}
	}

	@Test
	void testGetBusStopDepartureDetails() {
		try {
			String body = "[{\"Actual\":false,\"BlockNumber\":9,\"DepartureText\":\"1:25\","
					+ "\"DepartureTime\":\"\\/Date(1574450700000-0600)\\/\",\"Description\":\"to Mall of America\",\"Gate\":\"1\","
					+ "\"Route\":\"Blue\",\"RouteDirection\":\"SOUTHBOUND\",\"Terminal\":\"\",\"VehicleHeading\":0,"
					+ "\"VehicleLatitude\":0,\"VehicleLongitude\":0}]";
			ResponseEntity<String> response = Mockito.spy(ResponseEntity.status(HttpStatus.OK)).body(body);
			Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
					Mockito.any(HttpEntity.class), Mockito.any(Class.class))).thenReturn(response);

			TimepointDeparture dept = service.getDepartureDetails("901", Directions.NORTH, "TF22");
			assertEquals("1:25", dept.getDepartureText());
		} catch (ApplicationException | SystemException | NotFoundException e) {
			fail("Exception at testGetBusRoute: " + e.getLocalizedMessage());
		}
	}

//	@Test
//	public void testGetDepartureTimeFrom() {
//		try {
//			Route route = Mockito.eq(new Route("Metro Blue Line", "8", "901"));
//			BusStop stop = new BusStop("Target Field Station Platform 2", "TF22");
//			
//			
//			Mockito.when(service.getBusRoute(Mockito.anyString())).thenReturn(route);
//			
//			Mockito.when(service.getBusStopInRoute(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
//					.thenReturn(stop);
//			
//			TimepointDeparture dept = new TimepointDeparture(Mockito.anyString(), Mockito.anyString(),
//					Mockito.anyString(), "\\/Date(1574450700000-0600)\\", Mockito.anyString(), Mockito.anyString(),
//					Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
//					Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//			
//			Mockito.when(service.getDepartureDetails(Mockito.anyString(), Mockito.any(Directions.class),
//					Mockito.anyString())).thenReturn(dept);
//
//			service.getDepartureTimeFrom(
//					new BusMovement("Metro Blue Line", "North", "Target Field Station Platform 2"));
//		} catch (ApplicationException | SystemException | NotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
