package logic.controller.maps;

import logic.model.Position;

public class ViewMapHereApi extends HereApi implements ViewMapApi {

	private static ViewMapHereApi instance = null;

	private static final String API_NAME = "/mia/1.6/mapview";
	private static final int ZOOM = 15;

	private ViewMapHereApi() {
	}

	public static ViewMapHereApi getInstance() {
		if (ViewMapHereApi.instance == null)
			ViewMapHereApi.instance = new ViewMapHereApi();
		return ViewMapHereApi.instance;
	}

	@Override
	public String viewFromPos(Position p) {
		return this.viewFromPos(p, ZOOM);
	}

	@Override
	public String viewFromPos(Position p, int zoom) {
		StringBuilder builder = new StringBuilder();
		builder.append(SCHEME);
		builder.append(IMAGE_HOST);
		builder.append(API_NAME);
		builder.append("?apiKey=" + KEY);
		// coordinates of the point to visualize
		builder.append("&lat=" + p.getLat());
		builder.append("&lon=" + p.getLon());
		builder.append("&z=" + zoom);
		for (ViewMapHereParameter parameter : ViewMapHereParameter.values()) {
			builder.append(parameter.getParameter());
		}
		return builder.toString();
	}

}
