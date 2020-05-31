package logic.controller;

import org.w3c.dom.Element;

import logic.controller.httpclient.XmlDecoder;
import logic.model.Position;

public class PositionBuilder {

	private static final String LAT = "lat";
	private static final String LON = "lon";
	private static final String SCORE = "score";
	private static final String ADDRESS = "freeformAddress";

	private PositionBuilder() {
	}

	public static Position buildFromElement(Element element) {
		Double lat = Double.parseDouble(XmlDecoder.getStringByTag(element, LAT));
		Double lon = Double.parseDouble(XmlDecoder.getStringByTag(element, LON));
		Double score = Double.parseDouble(XmlDecoder.getStringByTag(element, SCORE));
		String address = XmlDecoder.getStringByTag(element, ADDRESS);
		return new Position(lat, lon, score, address);
	}
}
