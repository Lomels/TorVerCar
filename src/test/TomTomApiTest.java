package test;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.maps.GeodecodeMapsApi;
import logic.controller.maps.TomTomApi;
import logic.entity.Position;

import static org.junit.Assert.assertNotNull;

import java.util.*;
import java.util.logging.*;

public class TomTomApiTest {

	// TODO: implement test
	private static final Logger LOGGER = Logger.getLogger(TomTomApi.class.getName());

	@Test
	public void normalTest() {
		GeodecodeMapsApi api = TomTomApi.getInstance();
		List<Position> pos = new ArrayList<Position>();
		try {
			pos = api.addrToPos("via prenestina nuova 51");
		} catch (ApiNotReachableException e) {
			e.printStackTrace();
		}
		for (Position p : pos) {
			LOGGER.log(Level.INFO, p.toString() + "\n");
		}
		assertNotNull(pos);

	}
}
