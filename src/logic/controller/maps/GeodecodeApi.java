package logic.controller.maps;

import java.util.List;

import logic.model.Position;

/*
 * 	Interface for geographic decoding operation
 *	such as obtain the coordinates from an address
 */
public interface GeodecodeApi {

	public List<Position> addrToPos(String address);

}
