package test;

import logic.controller.exception.*;
import logic.controller.maps.*;
import logic.model.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

public class AdapterMapsApiTest {

	private static final Logger LOGGER = Logger.getLogger(AdapterMapsApiTest.class.getCanonicalName());
	private static final Boolean LOGGABLE = true;

	private static final MapsApi MAPS_API = AdapterMapsApi.getInstance();

	private static final String ADDRESS_1 = "Via Prenestina Nuova 51, Palestrina";
	private static final String ADDRESS_2 = "Via Folcarotonda 19, Palestrina";
	private static final String ADDRESS_3 = "Via Adua 10, Rocca Priora";

	private void log(String message, Boolean bool) {
		if (LOGGABLE && bool)
			LOGGER.info(message);
	}

	@Test
	public void addrToPosTest() throws ApiNotReachableException, InvalidInputException {
		List<Position> results = MAPS_API.addrToPos(ADDRESS_1);
		for (Position p : results)
			this.log(p.toString(), true);
		assertEquals(Position.class, results.get(0).getClass());
	}

	@Test
	public void startToStopTest() throws InvalidInputException, ApiNotReachableException {
		LocalDateTime startInterval = LocalDateTime.now();
		Position pickup, dropoff;
		pickup = MAPS_API.addrToPos(ADDRESS_2).get(0);
		dropoff = MAPS_API.addrToPos(ADDRESS_1).get(0);
		Route result = MAPS_API.startToStop(pickup, dropoff, startInterval);
		this.log(result.toStringShort(), false);
		assertEquals(Route.class, result.getClass());
	}

	@Test
	public void viewFromPTest() throws ApiNotReachableException, InvalidInputException {
		Position p = MAPS_API.addrToPos(ADDRESS_3).get(0);
		String result = MAPS_API.viewFromPos(p);
		this.log("\n" + result, false);
		assertNotNull(result);
	}

	@Test
	public void saveImageTest() throws ApiNotReachableException, InvalidInputException {
		Position p = MAPS_API.addrToPos(ADDRESS_1).get(0);
		MAPS_API.saveImage(p);
	}

	@Test
	public void viewRouteTest() throws ApiNotReachableException, InvalidInputException {
		LocalDateTime startInterval = LocalDateTime.now();
		Position pickup, dropoff;
		pickup = MAPS_API.addrToPos(ADDRESS_2).get(0);
		dropoff = MAPS_API.addrToPos(ADDRESS_1).get(0);
		Route route = MAPS_API.startToStop(pickup, dropoff, startInterval);
		String url = MAPS_API.viewFromRoute(route);
		Logger.getGlobal().info("URL:\n" + url);
	}
}
