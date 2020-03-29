package logic.controller.maps;

import logic.entity.Position;

public interface ViewMapsApi {

	public String viewFromPos(Position p);

	public String viewFromPos(Position p, int zoom);
	
	public void saveImage(Position p);
}
