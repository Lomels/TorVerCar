package logic.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Lift {
	
	private Integer liftID;
	private LocalDateTime startTime;
	private Integer maxDuration;
	private String note;
	
	private StudentCar driver;
	private List<Student> passengers = new ArrayList<>();
	
	private Route route;

	public Lift(Integer liftID, LocalDateTime startTime, int maxDuration, String note,
			StudentCar driver, List<Student> passengers, Route route) throws InvalidInputException {
		this.liftID = liftID;
		this.setStartTime(startTime);
		this.setMaxDuration(maxDuration);
		this.note = note;
		this.setDriver(driver);
		this.passengers = passengers;
		this.setRoute(route);
	}

	// TODO: implement lift database
	public Integer getLiftID() {
		return liftID;
	}

	public void setLiftID(Integer liftID) {
		this.liftID = liftID;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) throws InvalidInputException {
		if (startTime == null)
			throw new InvalidInputException("startTime must be not null.");
		this.startTime = startTime;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(Integer maxDuration) throws InvalidInputException {
		if (maxDuration == null || maxDuration <= 0) {
			throw new InvalidInputException("maxDuration must be not null and greater than 0.");
		}
		this.maxDuration = maxDuration;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public StudentCar getDriver() {
		return driver;
	}

	public void setDriver(StudentCar driver) throws InvalidInputException {
		InputChecker.checkNotNull(driver, "Driver");
		this.driver = driver;
	}

	public List<Student> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Student> passengers) {
		this.passengers = passengers;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) throws InvalidInputException {
		InputChecker.checkNotNull(route, "Route");
		this.route = route;
	}

	@Override
	public String toString() {
		return "Lift [liftID=" + liftID + ", startTime=" + startTime + ", maxDuration=" + maxDuration + ", note=" + note
				+ ", driver=" + driver + ", passengers=" + passengers + ", route=" + route + "]";
	}
	
	

}
