package logic.model;

import org.json.JSONException;
import org.json.JSONObject;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Position {

	private Double lat;
	private Double lon;
	private Double score;
	private String address;

	public Position(Double lat, Double lon, Double score, String address) throws InvalidInputException {
		super();
		this.setLat(lat);
		this.setLon(lon);
		this.score = score;
		this.address = address;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}

	public Double getScore() {
		return score;
	}

	public String getAddress() {
		return address;
	}

	public void setLat(Double lat) throws InvalidInputException {
		InputChecker.checkNotNull(lat, "Latitude");
		this.lat = lat;
	}

	public void setLon(Double lon) throws InvalidInputException {
		InputChecker.checkNotNull(lon, "Longitude");
		this.lon = lon;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatLon() {
		return getLat() + "," + getLon();
	}

	@Override
	public String toString() {
		return "Position [lat=" + lat + ", lon=" + lon + ",\nscore=" + score + ",\nlongAddress=" + address + "]";
	}

	public JSONObject JSONencode() {
		JSONObject result = new JSONObject();
		result.put("lat", this.getLat());
		result.put("lon", this.getLon());
		result.put("score", this.getScore());
		result.put("address", this.getAddress());
		return result;
	}

	public static Position JSONdecode(JSONObject json) throws JSONException, InvalidInputException {
		return new Position(json.getDouble("lat"), json.getDouble("lon"), json.getDouble("score"),
				json.getString("address"));
	}

}
