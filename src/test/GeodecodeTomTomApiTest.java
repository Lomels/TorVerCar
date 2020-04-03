package test;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.maps.DecodeTomTomApi;
import logic.controller.maps.GeodecodeMapsApi;
import logic.controller.maps.TomTomApi;
import logic.entity.Position;

import static org.junit.Assert.assertNotNull;

import java.util.*;
import java.util.logging.*;

public class GeodecodeTomTomApiTest {

	// TODO: implement test
	private static final Logger LOGGER = Logger.getLogger(TomTomApi.class.getName());
	private static final GeodecodeMapsApi geoApi = DecodeTomTomApi.getInstance();

	@Test
	public void normalTest() {
		List<Position> pos = new ArrayList<Position>();
		try {
			pos = geoApi.addrToPos("via prenestina nuova 51");
		} catch (ApiNotReachableException e) {
			e.printStackTrace();
		}
		for (Position p : pos) {
			LOGGER.log(Level.INFO, p.toString() + "\n");
		}
		assertNotNull(pos);

	}
}
