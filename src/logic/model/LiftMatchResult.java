package logic.model;

import java.time.LocalDateTime;

import logic.utilities.InputChecker;

public class LiftMatchResult {

	private Lift lift;
	private LocalDateTime relativeStartDateTime;
	private LocalDateTime relativeStopDateTime;

	public LiftMatchResult(Lift lift, LocalDateTime relativeStartDateTime, LocalDateTime relativeStopDateTime) {
		InputChecker.checkNotNull(lift, "Lift");
		this.lift = lift;
		InputChecker.checkNotNull(relativeStartDateTime, "RelativeStartDateTime");
		this.relativeStartDateTime = relativeStartDateTime;
		InputChecker.checkNotNull(relativeStopDateTime, "RelativeStopDateTime");
		this.relativeStopDateTime = relativeStopDateTime;
	}

	public Lift getLift() {
		return lift;
	}

	public LocalDateTime getRelativeStartDateTime() {
		return relativeStartDateTime;
	}

	public LocalDateTime getRelativeStopDateTime() {
		return relativeStopDateTime;
	}

	@Override
	public String toString() {
		return "LiftMatchResult [lift=" + lift + ",\nrelativeStartDateTime=" + relativeStartDateTime
				+ ", relativeStopDateTime=" + relativeStopDateTime + "]";
	}

}
