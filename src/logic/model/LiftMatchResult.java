package logic.model;

import java.time.LocalDateTime;

public class LiftMatchResult {

	private Lift lift;
	private LocalDateTime relativeStartDateTime;
	private LocalDateTime relativeStopDateTime;

	public LiftMatchResult(Lift lift, LocalDateTime relativeStartDateTime, LocalDateTime relativeStopDateTime) {
		this.lift = lift;
		this.relativeStartDateTime = relativeStartDateTime;
		this.relativeStopDateTime = relativeStopDateTime;
	}

	public Lift getLift() {
		return lift;
	}

	public void setLift(Lift lift) {
		this.lift = lift;
	}

	public LocalDateTime getRelativeStartDateTime() {
		return relativeStartDateTime;
	}

	public void setRelativeStartDateTime(LocalDateTime relativeStartDateTime) {
		this.relativeStartDateTime = relativeStartDateTime;
	}

	public LocalDateTime getRelativeStopDateTime() {
		return relativeStopDateTime;
	}

	public void setRelativeStopDateTime(LocalDateTime relativeStopDateTime) {
		this.relativeStopDateTime = relativeStopDateTime;
	}

}
