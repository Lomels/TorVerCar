package logic.view.booking;

import logic.model.Lift;
import logic.model.LiftMatchResult;

public class RowLift {
	private LiftMatchResult lift;
	private String from;
	private String to;
	private String timeFrom;
	private String timeTo;

	public RowLift(LiftMatchResult lift) {
		this.lift = lift;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	public String getTimeTo() {
		return timeTo;
	}
	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public LiftMatchResult getResult() {
		return this.lift;
	}
}
