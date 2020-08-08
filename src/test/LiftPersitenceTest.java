package test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Route;

public class LiftPersitenceTest {

	private void info(String name, Object object) {
		Logger.getGlobal().info(String.format("%s is: %s\n", name, object.toString()));
	}

	private void info(String message) {
		Logger.getGlobal().info(message + "\n");
	}

	@Test
	public void jsonEncrypt() throws ApiNotReachableException, InvalidInputException {

		MapsApi maps = AdapterMapsApi.getInstance();

		String addr1s = "Via Prenestina Nuova 51, Palestrina";
		String addr2s = "Viale del Politecnico 1, Roma";

		info("AddrToPos started");

		Position p1s = maps.addrToPos(addr1s).get(0);
		Position p2s = maps.addrToPos(addr2s).get(0);

		info("AddrToPos completed");

		List<Position> startStops = new ArrayList<Position>();
		startStops.add(p1s);
		startStops.add(p2s);

		Route routeStart = maps.startToStop(startStops);

		info("routeStart", routeStart);

		JSONObject routeEncoded = routeStart.JSONencode();

		info("routeStart encoded:", routeEncoded);

		String fromData = routeEncoded.toString();

		Route routeDecoded = Route.JSONdecode(new JSONObject(fromData));

		info("routeDcoded", routeDecoded);

	}
}
