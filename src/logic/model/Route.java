package logic.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import logic.controller.exception.InvalidInputException;
import logic.controller.exception.NoPositionFound;
import logic.utilities.InputChecker;

public class Route {

	private List<Position> stops;
	// in minutes
	private List<Integer> durations;
	// in meters
	private List<Integer> distances;

	public Route(List<Position> stops, List<Integer> durations, List<Integer> distances) throws InvalidInputException {
		this.setStops(stops);
		this.setDurations(durations);
		this.setDistances(distances);
	}

	// Stops method
	public List<Position> getStops() {
		return stops;
	}

	public void setStops(List<Position> stops) throws InvalidInputException {
		InputChecker.checkNotNull(stops, "Stops");
		this.stops = stops;
	}

	public Position getPickupPosition() {
		return this.stops.get(0);
	}

	public Position getDropoffPosition() {
		return this.stops.get(this.stops.size() - 1);
	}

	public Position getStop(Integer index) {
		return this.stops.get(index);
	}

	public Integer getStopIndex(Position position) {
		for (Integer i = 0; i < this.getStopsSize(); i++) {
			if (this.getStop(i).compare(position))
				return i;
		}
		throw new NoPositionFound();
	}

	public Integer getStopsSize() {
		return this.stops.size();
	}

	// Durations method
	public List<Integer> getDurations() {
		return this.durations;
	}

	public Integer getDurationUntilPosition(Position position) {
		Integer posIndex = this.getStopIndex(position);
		if (posIndex == 0)
			return 0;
		else
			return this.getDurations().get(posIndex - 1);
	}

	public void setDurations(List<Integer> durations) throws InvalidInputException {
		InputChecker.checkNotNull(durations, "Durations");
		this.durations = durations;
	}

	public Integer getTotalDuration() {
		return this.durations.get(this.durations.size() - 1);
	}

	// Distances method
	public List<Integer> getDistances() {
		return distances;
	}

	public Integer getTotalDistance() {
		return this.getDistances().get(this.getDistances().size() - 1);
	}

	public void setDistances(List<Integer> distances) throws InvalidInputException {
		InputChecker.checkNotNull(distances, "Distances");
		this.distances = distances;
	}

	// Other method
	public String toStringLong() {
		return "Route [stops=" + stops + ", durations=" + durations + ", distances=" + distances + "]";
	}

	@Override
	public String toString() {
		return "Route [stops=" + stops.size() + ", duration=" + this.getDurations() + " minutes, distance="
				+ this.getDistances() + "]";
	}

	public JSONObject jsonEncode() {
		JSONObject result = new JSONObject();
		JSONArray jsonDurations = new JSONArray();
		for (Integer duration : this.durations) {
			jsonDurations.put(duration);
		}
		result.put("durations", jsonDurations);
		JSONArray jsonDistances = new JSONArray();
		for (Integer distance : this.distances) {
			jsonDistances.put(distance);
		}
		result.put("distances", jsonDistances);
		JSONArray jsonStops = new JSONArray();
		for (Position stop : this.stops) {
			jsonStops.put(stop.jsonEncode());
		}
		result.put("stops", jsonStops);
		return result;
	}

	public static Route jsonDecode(JSONObject json) throws InvalidInputException {
		List<Position> stops = new ArrayList<>();
		JSONArray jsonStops = json.getJSONArray("stops");
		for (int i = 0; i < jsonStops.length(); i++) {
			JSONObject jsonStop = jsonStops.getJSONObject(i);
			stops.add(Position.jsonDecode(jsonStop));
		}

		List<Integer> durations = new ArrayList<>();
		JSONArray jsonDurations = json.getJSONArray("durations");
		for (int i = 0; i < jsonDurations.length(); i++) {
			durations.add(jsonDurations.getInt(i));
		}

		List<Integer> distances = new ArrayList<>();
		JSONArray jsonDistances = json.getJSONArray("distances");
		for (int i = 0; i < jsonDistances.length(); i++) {
			distances.add(jsonDistances.getInt(i));
		}

		return new Route(stops, durations, distances);

	}

	public boolean compare(Route other) {
		String jsonThis = this.jsonEncode().toString();
		String jsonOther = other.jsonEncode().toString();
		return jsonThis.equals(jsonOther);
	}

}
