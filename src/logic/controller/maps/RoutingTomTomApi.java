package logic.controller.maps;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import logic.controller.httpclient.MyHttpClient;
import logic.controller.httpclient.XmlDecoder;
import logic.model.Position;
import logic.model.Route;

public class RoutingTomTomApi extends TomTomApi implements RoutingApi {

	private static RoutingTomTomApi instance = null;

	// using Routing
	private static final String VERSION_NUMBER = "1";
	private static final String FORMAT = "%s/routing/%s/calculateRoute/%s/%s?key=%s";

	private String routingFormat;

	// constructor
	private RoutingTomTomApi() {
		this.routingFormat = String.format(FORMAT, SERVER, VERSION_NUMBER, "%s", EXT, KEY);
	}

	// singleton
	public static RoutingTomTomApi getInstance() {
		if (instance == null)
			return new RoutingTomTomApi();
		return instance;
	}

	// Redirects to the operation that takes input a List<>
	@Override
	public Route startToStop(Position pickup, Position dropoff, LocalDateTime startInterval) {
		List<Position> stops = new ArrayList<Position>();
		stops.add(pickup);
		stops.add(dropoff);
		return this.startToStop(stops, startInterval);
	}

	// Returns the route
	@Override
	public Route startToStop(List<Position> stops, LocalDateTime startIntervall) {
		// TODO: test if it works
		Route route = null;
		Integer duration = null;
		Integer distance = null;

		// compute distance and duration
		String xml = null;

		xml = this.xmlFromStops(stops);
		Element root = XmlDecoder.getRoot(xml);

		duration = Integer.parseInt(XmlDecoder.getStringByTag(root, "travelTimeInSeconds")) / 60;
		distance = Integer.parseInt(XmlDecoder.getStringByTag(root, "lengthInMeters"));

		// build the Route
		route = new Route(stops, duration, distance);

		return route;
	}

	// call the API
	private String xmlFromStops(List<Position> stops) {
		String xml = null;
		try {
			// create the url and send it to the superclass
			String urlField = "";
			for (int index = 0; index < stops.size(); index++) {
				Position stop = stops.get(index);
				urlField += stop.getLat() + "," + stop.getLon();
				if (index < stops.size() - 1)
					urlField += ":";
			}
			URI requestUrl = new URI(String.format(this.routingFormat, urlField));
			xml = MyHttpClient.getXmlFromUrl(requestUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;

	}

}
