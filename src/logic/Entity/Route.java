package Entity;

public class Route {
	Position pickupPosition;
	Position dropoffPosition;
	RangeTime startTime;
	RangeTime stopTime;
	
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
	public RangeTime getStartTime() {
		return startTime;
	}
	public void setStartTime(RangeTime startTime) {
		this.startTime = startTime;
	}
	public RangeTime getStopTime() {
		return stopTime;
	}
	public void setStopTime(RangeTime stopTime) {
		this.stopTime = stopTime;
	}	
}
