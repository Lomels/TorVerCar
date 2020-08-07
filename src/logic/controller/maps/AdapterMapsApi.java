package logic.controller.maps;

import java.time.LocalDateTime;
import java.util.List;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
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
	public List<Position> addrToPos(String address) throws ApiNotReachableException, InvalidInputException {
		return this.geodecode.addrToPos(address);
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff, LocalDateTime startInterval)
			throws InvalidInputException {
		return this.routing.startToStop(pickup, dropoff, startInterval);
	}

	@Override
	public Route startToStop(List<Position> stops, LocalDateTime startInterval) throws InvalidInputException {
		return this.routing.startToStop(stops, startInterval);
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
	public void saveImage(Position p) {
		this.viewmap.saveImage(p);
	}

	@Override
	public String viewFromRoute(Route route) {
		return this.viewroute.viewFromRoute(route);
	}

}
