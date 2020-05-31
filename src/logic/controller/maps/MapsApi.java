package logic.controller.maps;

/*
 * 	Interface for the complete service of the map
 * 	That should include geodecoding, routing and visualizing
 */
public interface MapsApi extends GeodecodeApi, RoutingApi, ViewMapApi, ViewRouteApi{

}
