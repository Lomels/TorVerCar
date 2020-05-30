package logic.model;

import java.util.ArrayList;
import java.util.List;

import logic.controller.exception.InvalidInputException;

public class Route {

	private List<Position> stops = new ArrayList<>();
	// in minutes
	private Integer duration;
	// in meters
	private Integer distance;

	public Route(List<Position> stops, Integer duration, Integer distance) {
		this.stops = stops;
		this.duration = duration;
		this.distance = distance;
	}

	// TODO: notNull required
	public Route(Position pickupPosition, Position dropoffPosition, Integer duration, Integer distance)
			throws InvalidInputException {
		this.stops.add(pickupPosition);
		this.stops.add(dropoffPosition);
		this.setDuration(duration);
		this.distance = distance;
	}

	public void addStop(Position stop) {
		// TODO: input check
		this.stops.add(stop);
	}

	public Position getPickupPosition() {
		return this.stops.get(0);
	}

	public Position getDropoffPosition() {
		return this.stops.get(this.stops.size() - 1);
	}

	public Position getIndexPosition(Integer index) {
		Position result = null;
		try {
			result = this.stops.get(index);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) throws InvalidInputException {
		if (duration > 0)
			this.duration = duration;
		else
			throw new InvalidInputException("Duration should be more than 0.");
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		// TODO: input check
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Route [stops=" + stops + ", duration=" + duration + ", distance=" + distance + "]";
	}

	public String toStringShort() {
		return "Route [duration=" + duration + " minutes, distance=" + distance + "m]";
	}

}
