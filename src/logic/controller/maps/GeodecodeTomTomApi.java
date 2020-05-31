package logic.controller.maps;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;

import logic.controller.PositionBuilder;
import logic.controller.httpclient.MyHttpClient;
import logic.controller.httpclient.XmlDecoder;
import logic.model.Position;

public class GeodecodeTomTomApi extends TomTomApi implements GeodecodeApi {

	// Implemented as a Singleton
	private static GeodecodeTomTomApi instance = null;


	// API
	private static final String VERSION_NUMBER = "2";
	private static final String FORMAT = "%s/search/%s/geocode/%s.%s?key=%s";

	private String geocodeFormat;

	// Costruttore
	private GeodecodeTomTomApi() {
		this.geocodeFormat = String.format(FORMAT, SERVER, VERSION_NUMBER, "%s", EXT, KEY);
	}

	// implemented as singleton
	public static GeodecodeTomTomApi getInstance() {
		if (instance == null)
			GeodecodeTomTomApi.instance = new GeodecodeTomTomApi();
		return instance;
	}

	// override the interface
	@Override
	public List<Position> addrToPos(String address) {
		List<Position> result = new ArrayList<Position>();
		// TODO find where to handle errors
		
		// load result xml as string
		String xml = null;
		xml = this.xmlFromAddr(address);

		// use our class to decode the xml response
		List<Element> elements = XmlDecoder.getElemFromNameParent(xml, "item", "results");
		for (Element e : elements)
			result.add(PositionBuilder.buildFromElement(e));

		return result;
	}

	// call the MyHttpClient
	private String xmlFromAddr(String address) {
		String xml = null;
		try {
			// create the url and send it to the superclass
			String urlAddress = address.trim().replaceAll("\\s", "%20");
			URI requestUrl = new URI(String.format(this.geocodeFormat, urlAddress));
			xml = MyHttpClient.getXmlFromUrl(requestUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
}
