package logic.controller.maps;

import java.util.List;

import logic.model.Position;
import logic.model.RangeTime;
import logic.model.Route;

public interface RoutingApi {

	public Route startToStop(Position pickup, Position dropoff, RangeTime startInterval);
	
	public Route startToStop(List<Position> stops, RangeTime startInterval);
}
