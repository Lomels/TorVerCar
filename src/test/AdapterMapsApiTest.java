package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.model.Position;
import logic.model.Route;
import test.utilities.TestLogger;
import test.utilities.TestUtilities;

public class AdapterMapsApiTest extends TestUtilities {

	private static TestLogger logger = new TestLogger(AdapterMapsApiTest.class.getCanonicalName());
	private static boolean isLogable = false;

	public AdapterMapsApiTest() {
		logger.isLogable(isLogable);
	}

//	@Test
	public void addrToPosTest() throws ApiNotReachableException, InvalidInputException {
		List<Position> results = maps.addrToPos(ADDR_GIU);
		for (Position p : results)
			logger.info("Position", p);
		assertNotNull(results);
	}

//	@Test
	public void startToStopTest() throws InvalidInputException, ApiNotReachableException {
		Position start;
		Position stop;
		start = maps.addrToPos(ADDR_MARCO).get(0);
		stop = maps.addrToPos(ADDR_UNI).get(0);
		Route result = maps.startToStop(start, stop);
		logger.info("Route", result);
		assertEquals(Route.class, result.getClass());
	}

//	@Test
	public void startToStopListTest() throws InvalidInputException, ApiNotReachableException {
		Position start;
		Position stop;
		start = maps.addrToPos(ADDR_MARCO).get(0);
		stop = maps.addrToPos(ADDR_UNI).get(0);
		List<Position> stops = new ArrayList<>();
		stops.add(start);
		stops.add(stop);
		Route result = maps.startToStop(stops);
		logger.info("Route", result);
		assertEquals(Route.class, result.getClass());
	}

//	@Test
	public void viewFromPTest() throws ApiNotReachableException, InvalidInputException {
		Position p = maps.addrToPos(ADDR_UNI).get(0);
		String result = maps.viewFromPos(p);
		logger.info("Image URL", result);
		assertNotNull(result);
	}

//	@Test
	public void viewFromPZoomTest() throws ApiNotReachableException, InvalidInputException {
		Position p = maps.addrToPos(ADDR_UNI).get(0);
		int zoom = 10;
		String result = maps.viewFromPos(p, zoom);
		logger.info("Image URL", result);
		assertNotNull(result);
	}

//	@Test
	public void viewRouteTest() throws ApiNotReachableException, InvalidInputException {
		Position start;
		Position stop;
		start = maps.addrToPos(ADDR_MARCO).get(0);
		stop = maps.addrToPos(ADDR_UNI).get(0);
		Route route = maps.startToStop(start, stop);
		String url = maps.viewFromRoute(route);
		logger.info("Route URL", url);
		assertNotNull(url);
	}

	@Test
	public void addInternalRouteTest() throws ApiNotReachableException, InvalidInputException {
		Position start = maps.addrToPos(ADDR_MARCO).get(0);
		Position inter1 = maps.addrToPos(ADDR_GIU).get(0);
		Position inter2 = maps.addrToPos(ADDR_ZAGA).get(0);
		Position stop = maps.addrToPos(ADDR_UNI).get(0);
		Route startingRoute = maps.startToStop(start, stop);
		List<Position> stops = new ArrayList<>();
		stops.add(inter1);
		stops.add(inter2);
		Route route = maps.addInternalRoute(startingRoute, stops);
		logger.info("Route with intermediate stops", route);
		assertNotNull(route);
	}
}
