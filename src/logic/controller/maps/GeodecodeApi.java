package logic.controller.maps;

import java.util.List;

import logic.controller.exception.ApiNotReachableException;
import logic.model.Position;

/*
 * 	Interface for geodecoding operation
 *	such as obtain the coordinates from an address
 */
public interface GeodecodeApi {

	public List<Position> addrToPos(String address) throws ApiNotReachableException;
	
}
