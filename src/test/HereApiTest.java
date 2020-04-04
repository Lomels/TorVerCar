package test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import org.junit.*;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.maps.*;
import logic.entity.Position;

public class HereApiTest {

	private static final String ADDRESS = "viale del cinema 8 zagarolo";
	private static final Logger LOGGER = Logger.getLogger(HereApiTest.class.getName());
	private static final GeodecodeApi MAPS_API = GeodecodeTomTomApi.getInstance();
	private static final ViewMapApi VIEW_API = ViewMapHereApi.getInstance();

	@Test
	public void normalTest() {
		String url = null;
		Position p = null;
		try {
			p = MAPS_API.addrToPos(ADDRESS).get(0);
		} catch (ApiNotReachableException e) {
			e.printStackTrace();
		}
		LOGGER.info(p.toString());
		url = VIEW_API.viewFromPos(p);
		LOGGER.info("\n" + url);
		assertNotNull(url);
	}
	
	@Test
	public void saveTest() {
		Position p = null;
		try {
			p = MAPS_API.addrToPos(ADDRESS).get(0);
		} catch (ApiNotReachableException e) {
			e.printStackTrace();
		}
		LOGGER.info(p.toString());
		VIEW_API.saveImage(p);
	}
}
