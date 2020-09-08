package logic.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;

public class LiftBean {
	private Integer liftID;
	private LocalDateTime startDateTime;
	private Integer maxDuration;
	private String note;
	private StudentCar driver;
	private List<Student> passengers = new ArrayList<>();

	private Route route;
	private Position startPos;
	private Position stopPos;
	
	public LiftBean() {}
	
	public Integer getLiftID() {
		return liftID;
	}

	public void setLiftID(Integer liftID) {
		this.liftID = liftID;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Integer getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(Integer maxDuration) {
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

	public Position getStartPos() {
		return startPos;
	}

	public void setStartPos(Position startPos) {
		this.startPos = startPos;
	}

	public Position getStopPos() {
		return stopPos;
	}

	public void setStopPos(Position stopPos) {
		this.stopPos = stopPos;
	}
}
