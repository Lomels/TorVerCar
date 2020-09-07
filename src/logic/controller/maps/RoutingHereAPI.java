package logic.controller.maps;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.httpclient.MyHttpClient;
import logic.model.Position;
import logic.model.Route;

public class RoutingHereAPI extends HereApi implements RoutingApi {

	private static final Logger LOGGER = Logger.getLogger(RoutingHereAPI.class.getCanonicalName());

	private static RoutingHereAPI instance = null;
	private static final String VERSION = "/v8";
	private static final String PATH = VERSION + "/routes";

	private Route bestRoute = null;

	public static RoutingApi getInstance() {
		if (instance == null)
			instance = new RoutingHereAPI();
		return instance;
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff) throws InvalidInputException, ApiNotReachableException {
		List<Position> stops = new ArrayList<>();
		stops.add(pickup);
		stops.add(dropoff);
		return this.startToStop(stops);
	}

	@Override
	public Route startToStop(List<Position> stops) throws InvalidInputException, ApiNotReachableException {
		Integer duration = null;
		Integer distance = null;

		// Build the URL for the request
		StringBuilder builder = new StringBuilder();
		builder.append(SCHEME);
		builder.append(ROUTE_HOST);
		builder.append(PATH);
		builder.append("?apiKey=" + KEY);

		for (RoutingHereParameter param : RoutingHereParameter.values()) {
			builder.append(param.getParameter());
		}

		// add in the URL all the stops
		this.setStopsParameter(builder, stops);

		try {
			URI uri = new URI(builder.toString());
			String json = MyHttpClient.getStringFromUrl(uri);
			JSONObject jsonObject = new JSONObject(json);
			JSONObject jsonRoute = jsonObject.getJSONArray("routes").getJSONObject(0);
			JSONArray sections = jsonRoute.getJSONArray("sections");

			List<Integer> durations = new ArrayList<>();
			List<Integer> distances = new ArrayList<>();
			distance = 0;
			duration = 0;
			for (int i = 0; i < sections.length(); i++) {
				JSONObject summary = sections.getJSONObject(i).getJSONObject("summary");
				duration += summary.getInt("duration") / 60;
				durations.add(duration);
				distance += summary.getInt("length");
				distances.add(distance);
			}

			return new Route(stops, durations, distances);

		} catch (URISyntaxException e) {
			LOGGER.fine(e.toString());
		}
		return null;

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

	@Override
	public Route addInternalRoute(Route startRoute, List<Position> addStops) throws InvalidInputException, ApiNotReachableException {
		List<Position> startStops = startRoute.getStops();

		PositionListCombiner combiner = new PositionListCombiner();

		List<List<Position>> updatedStops = combiner.combinePositions(startStops, addStops);

		this.bestRoute = null;

		for (List<Position> tempStops : updatedStops) {
			Route tempRoute = this.startToStop(tempStops);
			this.keepBetter(tempRoute);
		}

		return this.bestRoute;

	}

	private void keepBetter(Route tempRoute) {
		// Compares the duration, keeps the one with the smallest
		// If same duration, keeps the one with the smallest distance
		if (this.bestRoute == null || tempRoute.getTotalDuration() < this.bestRoute.getTotalDuration()
				|| (tempRoute.getTotalDuration().equals(this.bestRoute.getTotalDuration())
						&& tempRoute.getTotalDistance() < this.bestRoute.getTotalDistance())) {
			this.bestRoute = tempRoute;
		}
	}

}
