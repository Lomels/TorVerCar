package logic.controller.maps;

import logic.entity.Position;

public interface ViewMapApi {

	public String viewFromPos(Position p);

	public String viewFromPos(Position p, int zoom);
	
	public void saveImage(Position p);
}
