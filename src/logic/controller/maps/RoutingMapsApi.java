package logic.controller.maps;

import java.util.List;

import logic.entity.Position;
import logic.entity.RangeTime;
import logic.entity.Route;

public interface RoutingMapsApi {

	public Route startToStop(Position pickup, Position dropoff, RangeTime startInterval);
	
	public Route startToStop(Position pickup, Position dropoff, List<Position> stops, RangeTime startInterval);
}
