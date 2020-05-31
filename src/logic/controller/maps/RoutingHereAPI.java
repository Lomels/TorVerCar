package logic.controller.maps;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import logic.controller.httpclient.MyHttpClient;
import logic.model.Position;
import logic.model.Route;

public class RoutingHereAPI extends HereApi implements RoutingApi {

	private static RoutingHereAPI instance = null;
	private final String path = "/v8/routes";

	public static RoutingApi getInstance() {
		if (instance == null)
			instance = new RoutingHereAPI();
		return instance;
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff, LocalDateTime startInterval) {
		List<Position> stops = new ArrayList<Position>();
		stops.add(pickup);
		stops.add(dropoff);
		return this.startToStop(stops, startInterval);
	}

	@Override
	public Route startToStop(List<Position> stops, LocalDateTime startInterval) {
		Integer duration = null, distance = null;

		StringBuilder builder = new StringBuilder();
		builder.append(SCHEME);
		builder.append(ROUTE_HOST);
		builder.append(path);
		builder.append("?apiKey=" + KEY);

		for (RoutingHereParameter param : RoutingHereParameter.values()) {
			builder.append(param.getParameter());
		}
		this.setStopsParameter(builder, stops);

		try {
			URI uri = new URI(builder.toString());
			String json = MyHttpClient.getStringFromUrl(uri);
//			Logger.getGlobal().info("URL:\n" + uri + "\nJSON:\n" + json);
			JSONObject jsonObject = new JSONObject(json);
			JSONObject jsonRoute = jsonObject.getJSONArray("routes").getJSONObject(0);
			JSONArray sections = jsonRoute.getJSONArray("sections");

			distance = 0;
			duration = 0;
			for (int i = 0; i < sections.length(); i++) {
				JSONObject summary = sections.getJSONObject(i).getJSONObject("summary");
				duration += summary.getInt("duration") / 60;
				distance += summary.getInt("length");
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Route(stops, duration, distance);
	}

	private void setStopsParameter(StringBuilder builder, List<Position> stops) {
		for (int i = 0; i < stops.size(); i++) {
			String coordinates = stops.get(i).getLatLon();
			String parameter;
			if (i == 0) {
				parameter = "&origin=" + coordinates;
			} else if (i == stops.size() - 1) {
				parameter = "&destination=" + coordinates;
			} else {
				parameter = "&via=" + coordinates;
			}
			builder.append(parameter);
		}
	}

}
