package logic.controller.maps;

import java.util.List;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.model.Position;
import logic.model.Route;

/*
 * 	This adapter implements the complete service MapsApi
 * 	and redirects to the method of the implementation of the single service
 */
public class CompleteMapsApi implements MapsApi {

	private static CompleteMapsApi instance = null;
	private GeodecodeApi geodecode;
	private RoutingApi routing;
	private ViewMapApi viewmap;
	private ViewRouteApi viewroute;

	private CompleteMapsApi() {
		this.geodecode = GeodecodeTomTomApi.getInstance();
		this.routing = RoutingHereAPI.getInstance();
		this.viewmap = ViewMapHereApi.getInstance();
		this.viewroute = ViewRouteHereApi.getInstance();
	}

	public static CompleteMapsApi getInstance() {
		if (CompleteMapsApi.instance == null)
			return new CompleteMapsApi();
		return instance;
	}

	@Override
	public List<Position> addrToPos(String address) throws ApiNotReachableException, InvalidInputException {
		return this.geodecode.addrToPos(address);
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff) throws InvalidInputException, ApiNotReachableException {
		return this.routing.startToStop(pickup, dropoff);
	}

	@Override
	public Route startToStop(List<Position> stops) throws InvalidInputException, ApiNotReachableException {
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
	public Route addInternalRoute(Route startingRoute, List<Position> stops) throws InvalidInputException, ApiNotReachableException {
		return this.routing.addInternalRoute(startingRoute, stops);
	}

}
