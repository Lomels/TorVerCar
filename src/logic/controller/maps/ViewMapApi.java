package logic.controller.maps;

import logic.model.Position;

/*
 * 	Interface for operation to visualize maps
 * 	such as producing an image given a position
 */
public interface ViewMapApi {

	public String viewFromPos(Position p);

	public String viewFromPos(Position p, int zoom);
}
