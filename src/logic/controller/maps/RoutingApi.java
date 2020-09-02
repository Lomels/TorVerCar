package logic.controller.maps;

import java.util.List;

import logic.model.Position;
import logic.model.Route;

/*
 * 	Interface for routing operations
 * 	such as computing a route between different position
 */
public interface RoutingApi {

	public Route startToStop(Position pickup, Position dropoff);

	public Route startToStop(List<Position> stops);

	public Route addInternalRoute(Route startingRoute, List<Position> stops);
}
