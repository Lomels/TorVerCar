package logic.controller.maps;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.*;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import logic.controller.PositionBuilder;
import logic.controller.exception.ApiNotReachableException;
import logic.entity.Position;

public class TomTomApi implements MapsApi {

	private static MapsApi instance = null;

	// using GeoDecode
	private static final String KEY = "8xAoG57mmkjCxim04niX0NYPXAZMuAyV";
	private static final String VERSION_NUMBER = "2";
	private static final String EXT = "xml";
	private static final String FORMAT = "https://api.tomtom.com/search/%s/geocode/%s.%s?key=%s";
	private String url;

	// internal
	private static final int CACHE = 0;
	private static final int REFRESH = 1;
	private static final String FILE = "/src/logic/controller/maps/lastQuery.xml";
	private String lastQuery = null;
	private String file;

	// Logger
	private static final Logger LOGGER = Logger.getLogger(TomTomApi.class.getCanonicalName());

	private TomTomApi() {
		this.url = String.format(FORMAT, VERSION_NUMBER, "%s", EXT, KEY);
		String path = null;
		try {
			path = new File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.file = path + FILE;
	}

	// implemented as singleton
	public static MapsApi getInstance() {
		if (instance == null)
			TomTomApi.instance = new TomTomApi();
		return instance;
	}

	// override the interface
	@Override
	public List<Position> addrToPos(String address) throws ApiNotReachableException {
		if (address.equals(this.lastQuery)) {
			return this.addrToPos(address, CACHE);
		} else {
			return this.addrToPos(address, REFRESH);
		}
	}

	// internal call with mode
	private List<Position> addrToPos(String address, int mode) throws ApiNotReachableException {
		List<Position> result = new ArrayList<Position>();
		try {
			// load result as xml string
			String xml = null;
			switch (mode) {
			case CACHE:
				xml = new String(Files.readAllBytes(Paths.get(this.file)));
				if (xml.equals(""))
					throw new FileNotFoundException("The file was empty.");
				break;
			case REFRESH:
				this.lastQuery = address;
				xml = this.useRestApi(address);
				break;
			}

			// from string to document to get the results
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xml)));

			// get desired results
			Element root = doc.getDocumentElement();
			Element results = (Element) root.getElementsByTagName("results").item(0);
			NodeList listOfResults = results.getElementsByTagName("item");
			for (int i = 0; i < listOfResults.getLength(); i++) {
				Node node = listOfResults.item(i);
				if (node.getParentNode().getNodeName() == "results") {
					// TODO: implement build of position
					Element element = (Element) node;
					result.add(PositionBuilder.buildFromElement(element));
				}
			}
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

	// method to use the REST API
	private String useRestApi(String address) throws ApiNotReachableException {
		String xml = null;
		// TODO: implement a class to build better url
		String urlAddress = address.trim().replaceAll("\\s", "%20");
		String requestUrl = String.format(this.url, urlAddress);

		try {
			// create client e request
			HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
			HttpRequest req = HttpRequest.newBuilder(new URI(requestUrl)).GET().build();
			LOGGER.log(Level.INFO, "Request sent.\n");

			// generazione response
			HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
			xml = res.body();

			// scrittura su file per cache
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
			writer.write(xml);
			writer.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

}
