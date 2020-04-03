package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.*;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.*;
import logic.entity.*;

public class RoutingTomTomApiTest {

	private static final Logger LOGGER = Logger.getLogger(TomTomApi.class.getName());
	private static final GeodecodeMapsApi geoApi = DecodeTomTomApi.getInstance();
	private static final RoutingMapsApi routeApi = RoutingTomTomApi.GetInstance();

	private static final String ADDRESS_1 = "Via prenestina nuova 51, Palestrina";
	private static final String ADDRESS_2 = "Via adua 10, Rocca Priora";
	private static final String ADDRESS_3 = "Viale del cinema 8, Zagarolo";

	@Test
	public void normalTest() throws ApiNotReachableException, InvalidInputException {
		RangeTime now = new RangeTime(LocalDateTime.now(), LocalDateTime.now());
		Position pickup = geoApi.addrToPos(ADDRESS_1).get(0);
		Position dropoff = geoApi.addrToPos(ADDRESS_3).get(0);

		Route route = routeApi.startToStop(pickup, dropoff, now);

		LOGGER.info(route.toString() + "\n");
	}

	@Test
	public void normalStopsTest() throws ApiNotReachableException, InvalidInputException {
		RangeTime now = new RangeTime(LocalDateTime.now(), LocalDateTime.now());
		Position pickup = geoApi.addrToPos(ADDRESS_1).get(0);
		Position middle = geoApi.addrToPos(ADDRESS_2).get(0);
		Position dropoff = geoApi.addrToPos(ADDRESS_3).get(0);

		List<Position> stops = new ArrayList<Position>();
		stops.add(pickup);
		stops.add(middle);
		stops.add(dropoff);

		Route route = routeApi.startToStop(stops, now);

		LOGGER.info(route.toString() + "\n");
	}
}
