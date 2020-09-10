package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Route;

public class MarsegliaTestCase2 {

	private static final Double DURATION_MARGIN_MULTIPLIER = 0.15;
	private static final Double DISTANCE_MARGIN_MULTIPLIER = 0.15;

	@Test
	public void startToStopTest() throws ApiNotReachableException, InvalidInputException {
		MapsApi mapsApi = AdapterMapsApi.getInstance();
		String addressStart = "Via Prenestina Nuova 51, Palestrina";
		String addressStop = "Via del Politecnico 1, Roma";
		Integer expectedDuration = 30;
		Integer expectedDistance = 26 * 1000;

		Position posStart = mapsApi.addrToPos(addressStart).get(0);
		Position posStop = mapsApi.addrToPos(addressStop).get(0);
		List<Position> stops = new ArrayList<>();

		stops.add(posStart);
		stops.add(posStop);
		Route route = mapsApi.startToStop(stops);
		
		Integer deltaDistance = Math.abs(expectedDistance - route.getTotalDistance());
		Integer deltaDuration = Math.abs(expectedDuration - route.getTotalDuration());

		assertTrue(deltaDistance < expectedDistance * DISTANCE_MARGIN_MULTIPLIER
				&& deltaDuration < expectedDuration * DURATION_MARGIN_MULTIPLIER);
	}

}
