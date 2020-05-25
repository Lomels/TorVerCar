package logic.controller.maps;

import logic.model.Position;

public interface ViewMapApi {

	public String viewFromPos(Position p);

	public String viewFromPos(Position p, int zoom);
	
	public void saveImage(Position p);
}
