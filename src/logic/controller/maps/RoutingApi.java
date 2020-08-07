package logic.controller.maps;

import java.time.LocalDateTime;
import java.util.List;

import logic.controller.exception.InvalidInputException;
import logic.model.Position;
import logic.model.Route;

/*
 * 	Interface for routing operations
 * 	such as computing a route between different position
 */
public interface RoutingApi {

	public Route startToStop(Position pickup, Position dropoff, LocalDateTime startInterval) throws InvalidInputException;
	
	public Route startToStop(List<Position> stops, LocalDateTime startInterval) throws InvalidInputException;
}
