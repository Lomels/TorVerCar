package logic.controller.maps;

import java.util.List;

import logic.controller.exception.ApiNotReachableException;
import logic.model.Position;

public interface GeodecodeApi {

	public List<Position> addrToPos(String address) throws ApiNotReachableException;
	
}
