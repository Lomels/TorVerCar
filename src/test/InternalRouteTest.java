package test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Route;

public class InternalRouteTest {

	private void info(String name, Object object) {
		Logger.getGlobal().info(String.format("%s is: %s\n", name, object.toString()));
	}

	private void info(String message) {
		Logger.getGlobal().info(message);
	}

	@Test
	public void addInternalRoute() throws ApiNotReachableException, InvalidInputException {
		MapsApi maps = AdapterMapsApi.getInstance();

		String addr1s = "Via Prenestina Nuova 51, Palestrina";
		String addr2s = "Viale del Politecnico 1, Roma";
		String addr3s = "Via Cisternole 171, Frascati";

		String addr1a = "Via Folcarotonda 19, Palestrina";
		String addr2a = "Via Montpellier 1, Tor Vergata";

		info("AddrToPos started");

		Position p1s = maps.addrToPos(addr1s).get(0);
		Position p2s = maps.addrToPos(addr2s).get(0);
		Position p3s = maps.addrToPos(addr3s).get(0);

		Position p1a = maps.addrToPos(addr1a).get(0);
		Position p2a = maps.addrToPos(addr2a).get(0);

		info("AddrToPos completed");

		List<Position> startStops = new ArrayList<Position>();
		startStops.add(p1s);
		startStops.add(p3s);
		startStops.add(p2s);

		List<Position> addStops = new ArrayList<Position>();
		addStops.add(p1a);
		addStops.add(p2a);

		Route routeStart = maps.startToStop(startStops);

		info("routeStart", routeStart);

		Route bestRoute = maps.addInternalRoute(routeStart, addStops);

		info("bestRoute is: " + bestRoute.toStringLong() + "\n\n");
	}

}
