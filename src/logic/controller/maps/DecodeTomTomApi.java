package logic.controller.maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import org.xml.sax.InputSource;

import javafx.fxml.FXMLLoader;
import logic.controller.PositionBuilder;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.httpClient.XmlDecoder;
import logic.entity.Position;

public class DecodeTomTomApi extends TomTomApi implements GeodecodeMapsApi {

	private static DecodeTomTomApi instance = null;

	// Logger
	private static final Logger LOGGER = Logger.getLogger(DecodeTomTomApi.class.getCanonicalName());

	// using GeoDecode
	private static final String VERSION_NUMBER = "2";
	private static final String FORMAT = SERVER + "search/%s/geocode/%s.%s?key=%s";
	private static final String FILE = "src/logic/controller/maps/" + DecodeTomTomApi.class.getCanonicalName() + ".xml";

	private String geocodeFormat;
	private String lastQuery = null;
	private String file;


	// Costruttore
	private DecodeTomTomApi() {
		this.geocodeFormat = String.format(FORMAT, VERSION_NUMBER, "%s", EXT, KEY);
		try {
			this.file = new File(FILE).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// implemented as singleton
	public static DecodeTomTomApi getInstance() {
		if (instance == null)
			DecodeTomTomApi.instance = new DecodeTomTomApi();
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
			xml = super.useRestApi(requestUrl);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
}
