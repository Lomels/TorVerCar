package logic.controller.maps;

import java.util.List;

import logic.controller.exception.ApiNotReachableException;
import logic.entity.Position;

public interface GeodecodeMapsApi {

	public List<Position> addrToPos(String address) throws ApiNotReachableException;
	
}
