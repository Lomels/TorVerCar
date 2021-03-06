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

public class GeodecodeTomTomApi extends TomTomApi implements GeodecodeApi {
	
	private static final Logger LOGGER = Logger.getLogger(GeodecodeTomTomApi.class.getCanonicalName());

	// Implemented as a Singleton
	private static GeodecodeTomTomApi instance = null;

	// API
	private static final String FUNC = "/search";
	private static final String VERSION = "/2";
	private static final String API_NAME = "/geocode";
	private static final String EXT = ".json";

	// Costruttore
	private GeodecodeTomTomApi() {
	}

	// implemented as singleton
	public static GeodecodeTomTomApi getInstance() {
		if (instance == null)
			GeodecodeTomTomApi.instance = new GeodecodeTomTomApi();
		return instance;
	}

	// override the interface
	@Override
	public List<Position> addrToPos(String address) throws InvalidInputException, ApiNotReachableException {
		List<Position> positions = new ArrayList<>();

		StringBuilder builder = new StringBuilder();
		builder.append(SCHEME);
		builder.append(HOST);
		builder.append(FUNC);
		builder.append(VERSION);
		builder.append(API_NAME);

		this.addrToParameter(address, builder);

		builder.append("?key=" + KEY);

		URI uri = null;
		try {
			uri = new URI(builder.toString());
			String json = MyHttpClient.getStringFromUrl(uri);

			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonResults = jsonObject.getJSONArray("results");
			for (int i = 0; i < jsonResults.length(); i++) {
				JSONObject jsonResult = jsonResults.getJSONObject(i);

				JSONObject jsonPosition = jsonResult.getJSONObject("position");
				JSONObject jsonAddress = jsonResult.getJSONObject("address");

				positions.add(new Position(jsonPosition.getDouble("lat"), jsonPosition.getDouble("lon"),
						jsonResult.getDouble("score"), jsonAddress.getString("freeformAddress")));
			}
		} catch (URISyntaxException e1) {
			LOGGER.severe(e1.toString());
		} catch (NullPointerException e) {
			throw new ApiNotReachableException("TomTom API is not reachable now.");
		}
		return positions;
	}

	private void addrToParameter(String address, StringBuilder builder) {
		String urlAddress = "/" + address.trim().replaceAll("\\s", "%20") + EXT;
		builder.append(urlAddress);
	}
}
