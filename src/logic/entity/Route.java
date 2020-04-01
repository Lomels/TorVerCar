package logic.entity;


import logic.controller.exception.InvalidInputException;

public class Route {
	private Position pickupPosition;
	private Position dropoffPosition;
	private RangeTime startInterval;
	//espressa in minuti
	private Integer duration;
	
	//TODO: notNull required
	public Route(Position pickupPosition, Position dropoffPosition, RangeTime startInterval, Integer duration) throws InvalidInputException {
		this.pickupPosition = pickupPosition;
		this.dropoffPosition = dropoffPosition;
		this.startInterval = startInterval;
		this.setDuration(duration);
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
	
	public RangeTime getStopInterval() throws InvalidInputException {
		return this.getStartInterval().addMinutes(this.getDuration());
	}
		
}
