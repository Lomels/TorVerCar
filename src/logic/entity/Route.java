package logic.entity;


import logic.controller.exception.InvalidInputException;

public class Route {
	private Position pickupPosition;
	private Position dropoffPosition;
	private RangeTime startInterval;
	//espressa in minuti
	private Integer duration;
	private Integer distance;
	
	//TODO: notNull required
	public Route(Position pickupPosition, Position dropoffPosition, RangeTime startInterval, Integer duration, Integer distance) throws InvalidInputException {
		this.pickupPosition = pickupPosition;
		this.dropoffPosition = dropoffPosition;
		this.startInterval = startInterval;
		this.setDuration(duration);
		this.distance = distance;
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
		if(duration > 0)
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
		
}
