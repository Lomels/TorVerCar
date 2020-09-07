package logic.controller.maps;

import logic.model.Position;
import logic.model.Route;

public class ViewRouteHereApi extends HereApi implements ViewRouteApi {

	private static ViewRouteHereApi instance = null;

	private static final String VERSION = "/mia" + "/1.6";
	private static final String API_NAME = "/routing";

	public static ViewRouteHereApi getInstance() {
		if (ViewRouteHereApi.instance == null) {
			ViewRouteHereApi.instance = new ViewRouteHereApi();
		}
		return ViewRouteHereApi.instance;
	}

	@Override
	public String viewFromRoute(Route route) {
		StringBuilder builder = new StringBuilder();
		builder.append(SCHEME);
		builder.append(IMAGE_HOST);
		builder.append(VERSION);
		builder.append(API_NAME);
		builder.append("?apiKey=" + KEY);

		for (ViewRouteHereParameter parameter : ViewRouteHereParameter.values()) {
			builder.append(parameter.getParameter());
		}

		this.routeToParameters(route, builder);
		return builder.toString();
	}

	private void routeToParameters(Route route, StringBuilder builder) {
		for (int i = 0; i < route.getStopsSize(); i++) {
			Position position = route.getStop(i);
			builder.append("&waypoint" + i + "=" + position.getLatLon());
			builder.append("&poix" + i + "=" + position.getLatLon());
		}

	}

}
