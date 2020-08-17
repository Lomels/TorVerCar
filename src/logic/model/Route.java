package logic.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Route {

	private List<Position> stops;
	// in minutes
	private Integer duration;
	// in meters
	private Integer distance;

	public Route(List<Position> stops, Integer duration, Integer distance) throws InvalidInputException {
		this.stops = stops;
		this.setDuration(duration);
		this.setDistance(distance);
	}

	public Route(Position pickupPosition, Position dropoffPosition, Integer duration, Integer distance)
			throws InvalidInputException {
		this.stops = new ArrayList<>();
		this.stops.add(pickupPosition);
		this.stops.add(dropoffPosition);
		this.setDistance(distance);
		this.setDuration(duration);
	}

	public Position getPickupPosition() {
		return this.stops.get(0);
	}

	public Position getDropoffPosition() {
		return this.stops.get(this.stops.size() - 1);
	}

	public Position getStop(Integer index) {
		Position result = null;
		try {
			result = this.stops.get(index);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public Integer getStopsSize() {
		return this.stops.size();
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) throws InvalidInputException {
		this.duration = duration;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) throws InvalidInputException {
		if (distance == null || distance <= 0)
			throw new InvalidInputException("Distance must be not null and greater than 0.");
		this.distance = distance;
	}

	public List<Position> getStops() {
		return stops;
	}

	public void setStops(List<Position> stops) throws InvalidInputException {
		InputChecker.checkNotNull(stops, "Stops");
		this.stops = stops;
	}

	public String toStringLong() {
		return "Route [stops=" + stops + ", duration=" + duration + ", distance=" + distance + "]";
	}

	@Override
	public String toString() {
		return "Route [stops=" + stops.size() + ", duration=" + duration + " minutes, distance=" + distance + "m]";
	}
	
	public JSONObject jsonEncode() {
		JSONObject result = new JSONObject();
		result.put("duration", this.getDuration());
		result.put("distance", this.getDistance());
		JSONArray jsonStops = new JSONArray();
		for(Position stop : this.stops) {
			jsonStops.put(stop.jsonEncode());
		}
		result.put("stops", jsonStops);
		return result;
	}
	
	public static Route jsonDecode(JSONObject json) throws JSONException, InvalidInputException {
		Route result = new Route(null, json.getInt("duration"), json.getInt("distance"));
		List<Position> stops = new ArrayList<>();
		JSONArray jsonStops = json.getJSONArray("stops");
		for(int i = 0; i < jsonStops.length(); i++) {
			JSONObject jsonStop = jsonStops.getJSONObject(i);
			stops.add(Position.jsonDecode(jsonStop));
		}
		result.setStops(stops);
		return result;
	}

}
