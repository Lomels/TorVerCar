package logic.controller.maps;

import java.util.List;

import logic.model.Position;
import logic.model.Route;

/*
 * 	This adapter implements the complete service MapsApi
 * 	and redirects to the method of the implementation of the single service
 */
public class AdapterMapsApi implements MapsApi {

	private static AdapterMapsApi instance = null;
	private GeodecodeApi geodecode;
	private RoutingApi routing;
	private ViewMapApi viewmap;
	private ViewRouteApi viewroute;

	private AdapterMapsApi() {
		this.geodecode = GeodecodeTomTomApi.getInstance();
		this.routing = RoutingHereAPI.getInstance();
		this.viewmap = ViewMapHereApi.getInstance();
		this.viewroute = ViewRouteHereApi.getInstance();
	}

	public static AdapterMapsApi getInstance() {
		if (AdapterMapsApi.instance == null)
			return new AdapterMapsApi();
		return instance;
	}

	@Override
	public List<Position> addrToPos(String address) {
		return this.geodecode.addrToPos(address);
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff) {
		return this.routing.startToStop(pickup, dropoff);
	}

	@Override
	public Route startToStop(List<Position> stops) {
		return this.routing.startToStop(stops);
	}

	@Override
	public String viewFromPos(Position p) {
		return this.viewmap.viewFromPos(p);
	}

	@Override
	public String viewFromPos(Position p, int zoom) {
		return this.viewmap.viewFromPos(p, zoom);
	}

	@Override
	public String viewFromRoute(Route route) {
		return this.viewroute.viewFromRoute(route);
	}

	@Override
	public Route addInternalRoute(Route startingRoute, List<Position> stops) {
		return this.routing.addInternalRoute(startingRoute, stops);
	}

}
