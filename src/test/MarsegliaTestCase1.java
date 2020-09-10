package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;

public class MarsegliaTestCase1 {

	private static final Double DOUBLE_MARGIN = 0.01;

	@Test
	public void addrToPosTest() throws ApiNotReachableException, InvalidInputException {
		MapsApi mapsApi = AdapterMapsApi.getInstance();
		String address = "Via del Politecnico 1, Roma";
		Double expectedLat = 41.855882;
		Double expectedLon = 12.622651;
		List<Position> results = mapsApi.addrToPos(address);
		Position firstResult = results.get(0);
		Double actualLat = firstResult.getLat();
		Double actualLon = firstResult.getLon();
		Double deltaLat = Math.abs(expectedLat - actualLat);
		Double deltaLon = Math.abs(expectedLon - actualLon);
		assertTrue(deltaLat < DOUBLE_MARGIN && deltaLon < DOUBLE_MARGIN);
	}

}
