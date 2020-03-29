package logic.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Element;

import logic.entity.Position;

public class PositionBuilder {

	private static final String LAT = "lat";
	private static final String LON = "lon";
	private static final String SCORE = "score";
	private static final String ADDRESS = "freeformAddress";

	private static final Logger LOGGER = Logger.getLogger(PositionBuilder.class.getName());

	private static String getByTag(Element element, String tag) {
		try {
			return element.getElementsByTagName(tag).item(0).getTextContent();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static Position buildFromElement(Element element) {
		Double lat = Double.parseDouble(getByTag(element, LAT));
		Double lon = Double.parseDouble(getByTag(element, LON));
		Double score = Double.parseDouble(getByTag(element, SCORE));
		String address = getByTag(element, ADDRESS);
		return new Position(lat, lon, score, address);
	}
}
