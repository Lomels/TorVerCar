package logic.controller.maps;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.controller.exception.InvalidInputException;
import logic.controller.httpclient.MyHttpClient;
import logic.model.Position;

public class GeodecodeTomTomApi extends TomTomApi implements GeodecodeApi {

	// Implemented as a Singleton
	private static GeodecodeTomTomApi instance = null;

	// API
	private static final String path = "/search/2/geocode";
	private static final String ext = ".json";

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
	public List<Position> addrToPos(String address) throws InvalidInputException {
		List<Position> positions = new ArrayList<>();
		// TODO find where to handle errors

		StringBuilder builder = new StringBuilder();
		builder.append(SCHEME);
		builder.append(HOST);
		builder.append(path);

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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return positions;
	}

	private void addrToParameter(String address, StringBuilder builder) {
		String urlAddress = "/" + address.trim().replaceAll("\\s", "%20") + ext;
		builder.append(urlAddress);
	}
}
