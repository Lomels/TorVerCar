package test;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.GeodecodeTomTomApi;
import logic.controller.maps.GeodecodeApi;
import logic.controller.maps.TomTomApi;
import logic.model.Position;

import static org.junit.Assert.assertNotNull;

import java.util.*;
import java.util.logging.*;

public class GeodecodeTomTomApiTest {

	// TODO: implement test
	private static final Logger LOGGER = Logger.getLogger(TomTomApi.class.getName());
	private static final GeodecodeApi geoApi = GeodecodeTomTomApi.getInstance();

	@Test
	public void normalTest() throws InvalidInputException {
		List<Position> pos = new ArrayList<Position>();
		try {
			pos = geoApi.addrToPos(" zagarolo");
		} catch (ApiNotReachableException e) {
			e.printStackTrace();
		}
		for (Position p : pos) {
			LOGGER.log(Level.INFO, p.toString() + "\n");
		}
		assertNotNull(pos);

	}
}
