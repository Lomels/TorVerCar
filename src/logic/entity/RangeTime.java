package logic.entity;

import java.time.*;

public class RangeTime {
	//TODO: throw exception
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
			// TODO Implementare meglio
			return;
		}
		this.startTime = time;
	}
	
	public LocalDateTime getStopTime() {
		return this.stopTime;
	}
	
	public void setStopTime(LocalDateTime time) {
		if(time.isBefore(this.startTime)){
			// TODO Implementare meglio
			return;
		}
		this.stopTime = time;
	}
}
