package logic.view.booking;

import logic.model.Lift;

public class RowLift {
	private Lift lift;
	private String from;
	private String to;
	private String timeFrom;
	private String timeTo;
	
	public RowLift(Lift lift) {
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

	public Lift getLift() {
		return this.lift;
	}
}
