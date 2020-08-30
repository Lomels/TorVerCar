package logic.view.mylift;

public class RowMyLift {
	private String from;
	private String to;
	private String when;
	private String start;
	private String stop;

	public RowMyLift(String from, String to, String when, String start, String stop) {
		this.setFrom(from);
		this.setTo(to);
		this.setWhen(when);
		this.setStart(start);
		this.setStop(stop);
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}
}
