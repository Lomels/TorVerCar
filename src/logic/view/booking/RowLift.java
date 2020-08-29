package logic.view.booking;

public class RowLift {
	private String from;
	private String to;
	private String timeFrom;
	private String timeTo;
	
	public RowLift(String from, String to, String timeFrom, String timeTo) {
		this.from = from;
		this.to = to;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
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
}
