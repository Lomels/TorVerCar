package logic.entity;

import java.util.ArrayList;
import java.util.List;

import logic.controller.exception.InvalidInputException;

public class Route {
	private Position pickupPosition;
	private Position dropoffPosition;
	// TODO: very urgent: add stops list
	private List<Position> stops = new ArrayList<Position>();
	
	private RangeTime startInterval;
	// espressa in minuti
	private Integer duration;
	private Integer distance;

	// TODO: notNull required
	public Route(Position pickupPosition, Position dropoffPosition, RangeTime startInterval, Integer duration,
			Integer distance) throws InvalidInputException {
		List<Position> stops = new ArrayList<Position>();
		this.stops.add(pickupPosition);
		this.stops.add(dropoffPosition);
		this.startInterval = startInterval;
		this.setDuration(duration);
		this.distance = distance;
	}
	
	public Route(List<Position> stops, RangeTime startInterval, Integer duration, Integer distance) {
		//TODO: constructor with stop list
	}

	public Position getPickupPosition() {
		return pickupPosition;
	}

	public void setPickupPosition(Position pickupPosition) {
		this.pickupPosition = pickupPosition;
	}

	public Position getDropoffPosition() {
		return dropoffPosition;
	}

	public void setDropoffPosition(Position dropoffPosition) {
		this.dropoffPosition = dropoffPosition;
	}

	public RangeTime getStartInterval() {
		return startInterval;
	}

	public void setStartInterval(RangeTime startTime) {
		this.startInterval = startTime;
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
		this.distance = distance;
	}

	public RangeTime getStopInterval() throws InvalidInputException {
		return this.getStartInterval().addMinutes(this.getDuration());
	}

//	@Override
//	public String toString() {
//		return "Route [pickupPosition=" + pickupPosition.getAddress() + ",\ndropoffPosition="
//				+ dropoffPosition.getAddress() + ",\nstartInterval=" + startInterval.getStartTime() + ", duration="
//				+ duration + ", distance=" + distance + "]";
//	}

}
