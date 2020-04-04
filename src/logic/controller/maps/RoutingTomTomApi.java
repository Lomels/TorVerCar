package logic.controller.maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.httpClient.XmlDecoder;
import logic.entity.Position;
import logic.entity.RangeTime;
import logic.entity.Route;

public class RoutingTomTomApi extends TomTomApi implements RoutingApi {

	private static RoutingTomTomApi instance = null;
	// Logger
	private final static Logger LOGGER = Logger.getLogger(RoutingTomTomApi.class.getCanonicalName());

	// using Routing
	private static final String VERSION_NUMBER = "1";
	private static final String FORMAT = SERVER + "routing/%s/calculateRoute/%s/%s?key=%s";
	private static final String FILE = "src/logic/controller/maps/" + RoutingTomTomApi.class.getCanonicalName()
			+ ".xml";

	private String routingFormat;
	private List<Position> lastQuery = null;
	// private String file;

	// constructor
	private RoutingTomTomApi() {
		this.routingFormat = String.format(FORMAT, VERSION_NUMBER, "%s", EXT, KEY);
		try {
			this.file = new File(FILE).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// singleton
	// TODO check if it is a good idea, maybe if two users uses it in the sametime
	// it breaks?
	// probably the cache will break
	public static RoutingTomTomApi getInstance() {
		if (instance == null)
			return new RoutingTomTomApi();
		return instance;
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff, RangeTime startInterval) {

		List<Position> allStops = new ArrayList<Position>();
		allStops.add(pickup);
		allStops.add(dropoff);
		return this.startToStop(allStops, startInterval);
	}

	@Override
	public Route startToStop(List<Position> stops, RangeTime startIntervall) {
		// TODO: build the route
		// TODO: test if it works
		Route route = null;
		Integer duration = null;
		Integer distance = null;
		Position pickup, dropoff;

		List<Integer> result = this.evaluate(stops);
		duration = result.get(0);
		distance = result.get(1);

		pickup = stops.get(0);
		dropoff = stops.get(stops.size() - 1);
		try {
			route = new Route(pickup, dropoff, startIntervall, duration, distance);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return route;
	}

	private List<Integer> evaluate(List<Position> stops) {
		if (this.lastQuery != null && this.lastQuery.equals(stops))
			return this.evaluate(stops, CACHE);
		return this.evaluate(stops, REFRESH);
	}

	private List<Integer> evaluate(List<Position> stops, int mode) {
		// TODO find a better a solution two transmit the distance and duration
		ArrayList<Integer> result = new ArrayList<Integer>();
		Integer duration = null;
		Integer distance = null;
		try {
			String xml = null;
			switch (mode) {
			case CACHE:
				LOGGER.info("Using cache mode.");
				xml = new String(Files.readAllBytes(Paths.get(this.file)));
				if (xml.equals(""))
					throw new FileNotFoundException("The file was empty.");
				break;
			case REFRESH:
				LOGGER.info("Using refresh mode.");
				// TODO add null control
				this.lastQuery = stops;
				xml = this.xmlFromStops(stops);
				break;
			}

			//TODO: implement XmlDecoder single element
			duration = Integer.parseInt(
					XmlDecoder.getElemFromNameParent(xml, "travelTimeInSeconds", "summary").get(0).getTextContent())
					/ 60;
			distance = Integer.parseInt(
					XmlDecoder.getElemFromNameParent(xml, "lengthInMeters", "summary").get(0).getTextContent());
			result.add(duration);
			result.add(distance);

		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

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
			xml = super.useRestApi(requestUrl, this.file);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApiNotReachableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;

	}

}
