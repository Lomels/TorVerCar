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
import java.util.logging.Level;
import java.util.logging.Logger;



import org.w3c.dom.*;


import logic.controller.PositionBuilder;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.httpclient.XmlDecoder;
import logic.entity.Position;

public class GeodecodeTomTomApi extends TomTomApi implements GeodecodeApi {

	private static GeodecodeTomTomApi instance = null;

	// Logger
	private static final Logger LOGGER = Logger.getLogger(GeodecodeTomTomApi.class.getCanonicalName());

	// using GeoDecode
	private static final String VERSION_NUMBER = "2";
	private static final String FORMAT = SERVER + "search/%s/geocode/%s.%s?key=%s";
	private static final String FILE = "src/logic/controller/maps/" + GeodecodeTomTomApi.class.getCanonicalName() + ".xml";

	private String geocodeFormat;
	private String lastQuery = null;
	private String file;


	// Costruttore
	private GeodecodeTomTomApi() {
		this.geocodeFormat = String.format(FORMAT, VERSION_NUMBER, "%s", EXT, KEY);
		try {
			this.file = new File(FILE).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// implemented as singleton
	public static GeodecodeTomTomApi getInstance() {
		if (instance == null)
			GeodecodeTomTomApi.instance = new GeodecodeTomTomApi();
		return instance;
	}

	// override the interface
	@Override
	public List<Position> addrToPos(String address) throws ApiNotReachableException {
		if (address.equals(this.lastQuery)) 
			return this.addrToPos(address, CACHE);
		return this.addrToPos(address, REFRESH);
	}

	// internal call with mode
	private List<Position> addrToPos(String address, int mode) throws ApiNotReachableException {
		List<Position> result = new ArrayList<Position>();
		try {
			// load result as xml string
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
				this.lastQuery = address;
				xml = this.xmlFromAddress(address);
				break;
			}

			// use our class to decode the xml response
			List<Element> elements = XmlDecoder.getElemFromNameParent(xml, "item", "results");
			for(Element e : elements)
				result.add(PositionBuilder.buildFromElement(e));

		} catch (FileNotFoundException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			this.addrToPos(address, REFRESH);
		} catch (ApiNotReachableException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			throw e;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Could not load the cache file.");
			this.addrToPos(address, REFRESH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// call the superclass
	private String xmlFromAddress(String address) throws ApiNotReachableException {
		String xml = null;
		try {
			// create the url and send it to the superclass
			String urlAddress = address.trim().replaceAll("\\s", "%20");
			URI requestUrl = new URI(String.format(this.geocodeFormat, urlAddress));
			xml = super.useRestApi(requestUrl, this.file);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
}
