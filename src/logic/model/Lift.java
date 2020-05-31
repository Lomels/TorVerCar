package logic.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Lift {
	private String liftID;
	private LocalDateTime startTime;
	private int maxDistance, maxDuration;
	private String note;

	private StudentCar driver;
	private List<Student> passengers = new ArrayList<Student>();
	private Route route;

	public Lift(String liftID, LocalDateTime startTime, int maxDistance, int maxDuration, String note,
			StudentCar driver, List<Student> passengers, Route route) {
		super();
		this.liftID = liftID;
		this.startTime = startTime;
		this.maxDistance = maxDistance;
		this.maxDuration = maxDuration;
		this.note = note;
		this.driver = driver;
		this.passengers = passengers;
		this.route = route;
	}

	public String getLiftID() {
		return liftID;
	}

	public void setLiftID(String liftID) {
		this.liftID = liftID;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(int maxDuration) {
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

	public void setDriver(StudentCar driver) {
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

	public void setRoute(Route route) {
		this.route = route;
	}

}
