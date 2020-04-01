package logic.entity;

import java.time.*;
import java.time.temporal.ChronoUnit;

import logic.controller.exception.InvalidInputException;

public class RangeTime {
	private LocalDateTime startTime;
	private LocalDateTime stopTime;

	public RangeTime(LocalDateTime startTime, LocalDateTime stopTime) throws InvalidInputException {
		this.setStartTime(startTime);
		this.setStopTime(stopTime);
	}

	public void setStartTime(LocalDateTime time) throws InvalidInputException {
		//TODO: this should be done not here
//		if (time.isBefore(LocalDateTime.now())) {
//			throw new InvalidInputException("Start time must not be before now");
//		}
		this.startTime = time;
	}

	public LocalDateTime getStartTime() {
		return this.startTime;
	}

	public void setStopTime(LocalDateTime time) throws InvalidInputException {
		if (time.isBefore(this.startTime)) {
			throw new InvalidInputException("Stop time must not be before start time");
		}
		this.stopTime = time;
	}

	public LocalDateTime getStopTime() {
		return this.stopTime;
	}
	
	public RangeTime addMinutes(int minutes) throws InvalidInputException {
		LocalDateTime newStart, newStop;
		newStart = this.getStartTime().plus(minutes, ChronoUnit.MINUTES);
		newStop = this.getStartTime().plus(minutes, ChronoUnit.MINUTES);
		return new RangeTime(newStart, newStop);
	}

}