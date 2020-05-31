package logic.controller.maps;

import java.time.LocalDateTime;
import java.util.List;

import logic.controller.exception.ApiNotReachableException;
import logic.model.Position;
import logic.model.Route;

/*
 * 	This adapter implementens the complete service MapsApi
 * 	and redirects to the method of the implementation of the single service
 */
public class AdapterMapsApi implements MapsApi {
	
	private static AdapterMapsApi instance = null;
	private static GeodecodeApi geodecode;
	private static RoutingApi routing;
	private static ViewMapApi viewmap;
	
	private AdapterMapsApi() {
		geodecode = GeodecodeTomTomApi.getInstance();
		routing = RoutingTomTomApi.getInstance();
		viewmap = ViewMapHereApi.getInstance();
	}
	
	public static AdapterMapsApi getInstance() {
		if(AdapterMapsApi.instance == null)
			return new AdapterMapsApi();
		return instance;
	}

	@Override
	public List<Position> addrToPos(String address) throws ApiNotReachableException {
		return geodecode.addrToPos(address);
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff, LocalDateTime startInterval) {
		return routing.startToStop(pickup, dropoff, startInterval);
	}

	@Override
	public Route startToStop(List<Position> stops, LocalDateTime startInterval) {
		return routing.startToStop(stops, startInterval);
	}

	@Override
	public String viewFromPos(Position p) {
		return viewmap.viewFromPos(p);
	}

	@Override
	public String viewFromPos(Position p, int zoom) {
		return viewmap.viewFromPos(p, zoom);
	}

	@Override
	public void saveImage(Position p) {
		viewmap.saveImage(p);
	}

}
