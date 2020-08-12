package test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import org.junit.*;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.*;
import logic.model.Position;

public class HereApiTest {

	private static final String ADDRESS = "viale del cinema 8 zagarolo";
	private static final Logger LOGGER = Logger.getLogger(HereApiTest.class.getName());
	private static final GeodecodeApi MAPS_API = GeodecodeTomTomApi.getInstance();
	private static final ViewMapApi VIEW_API = ViewMapHereApi.getInstance();

	@Test
	public void normalTest() throws InvalidInputException, ApiNotReachableException {
		String url = null;
		Position p = null;

		p = MAPS_API.addrToPos(ADDRESS).get(0);

		LOGGER.info(p.toString());
		url = VIEW_API.viewFromPos(p, 15);
		LOGGER.info("\n" + url);
		assertNotNull(url);
	}

//	@Test
	public void saveTest() throws ApiNotReachableException, InvalidInputException {
		Position p = null;

		p = MAPS_API.addrToPos(ADDRESS).get(0);

		LOGGER.info(p.toString());
		VIEW_API.saveImage(p);
	}
}
