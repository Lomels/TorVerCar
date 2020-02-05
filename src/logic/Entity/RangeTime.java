package Entity;

import java.time.*;

public class RangeTime {
	private LocalDateTime startTime;
	private LocalDateTime stopTime;
	
	public RangeTime(LocalDateTime startTime, LocalDateTime stopTime) {
		this.startTime = startTime;
		this.stopTime = stopTime;
	}
	
	public LocalDateTime getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(LocalDateTime time) {
		if(time.isBefore(LocalDateTime.now())) {
			return;
		}
		this.startTime = time;
	}
	
	public LocalDateTime getStopTime() {
		return this.stopTime;
	}
	
	public void setStopTime(LocalDateTime time) {
		if(time.isBefore(this.startTime)){
			return;
		}
		this.stopTime = time;
	}
}
