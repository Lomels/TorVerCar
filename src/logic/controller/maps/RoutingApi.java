package logic.controller.maps;

import java.time.LocalDateTime;
import java.util.List;

import logic.model.Position;
import logic.model.Route;

public interface RoutingApi {

	public Route startToStop(Position pickup, Position dropoff, LocalDateTime startInterval);
	
	public Route startToStop(List<Position> stops, LocalDateTime startInterval);
}
