package logic.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Lift {

	private Integer liftID;
	private LocalDateTime startDateTime;
	private Integer maxDuration;
	private String note;

	private StudentCar driver;
	private List<Student> passengers = new ArrayList<>();

	private Route route;

	public Lift(Integer liftID, LocalDateTime startDateTime, int maxDuration, String note, StudentCar driver,
			List<Student> passengers, Route route) throws InvalidInputException {
		this.liftID = liftID;
		this.setStartDateTime(startDateTime);
		this.setMaxDuration(maxDuration);
		this.note = note;
		this.setDriver(driver);
		this.setPassengers(passengers);
		this.setRoute(route);
	}

	public Integer getLiftID() {
		return liftID;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) throws InvalidInputException {
		InputChecker.checkNotNull(startDateTime, "startDateTime");
		this.startDateTime = startDateTime;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(Integer maxDuration) throws InvalidInputException {
		InputChecker.checkIntegerMoreThan("maxDuration", maxDuration, 0);
		this.maxDuration = maxDuration;
	}

	public String getNote() {
		return note;
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
		if (passengers == null)
			this.passengers = new ArrayList<>();
		else
			this.passengers = passengers;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) throws InvalidInputException {
		InputChecker.checkNotNull(route, "Route");
		this.route = route;
	}

	public LocalDateTime getStopDateTime() {
		return this.getStartDateTime().plusMinutes(this.getRoute().getTotalDuration());
	}

	public Integer getFreeSeats() {
		Integer carSeats = this.getDriver().getCarInfo().getSeats();
		return carSeats - this.getPassengers().size();
	}

	public boolean isPassenger(Student student) {
		if (this.getPassengers() == null)
			return false;
		else {
			for (Student passenger : this.getPassengers()) {
				if (student.getUserID().equals(passenger.getUserID()))
					return true;
			}
			return false;
		}
	}

	@Override
	public String toString() {
		return "Lift [liftID=" + liftID + ",\nstartDateTime=" + startDateTime + ", stopDateTime*="
				+ this.getStopDateTime() + ",\nmaxDuration=" + maxDuration + ", note=" + note + ",\ndriver=" + driver
				+ ",\npassengers=" + passengers + ",\nroute=" + route.toString() + "\n]";
	}

	// Returns true if two lift are the same expect for the liftID
	public boolean compare(Lift other) {
		boolean sameStartDateTime = this.getStartDateTime().equals(other.getStartDateTime());
		boolean sameMaxDuration = this.getMaxDuration() == other.getMaxDuration();
		boolean sameNote = this.getNote().equals(other.getNote());
		boolean sameDriver = this.getDriver().getUserID().equals(other.getDriver().getUserID());
		boolean samePassengers = true;
		try {
			for (int index = 0; index < this.getPassengers().size(); index++) {
				Student thisPassenger = this.getPassengers().get(index);
				Student otherPassenger = other.getPassengers().get(index);
				if (!thisPassenger.getUserID().equals(otherPassenger.userID)) {
					samePassengers = false;
					break;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
		boolean sameRoute = this.getRoute().compare(other.getRoute());
		return sameStartDateTime && sameMaxDuration && sameNote && sameDriver && samePassengers && sameRoute;
	}

	public boolean compareWithID(Lift other) {
		boolean sameLiftID;
		if (this.getLiftID() == null && other.getLiftID() == null) {
			sameLiftID = true;
		} else if (this.getLiftID() == null && other.getLiftID() != null) {
			sameLiftID = false;
		} else {
			sameLiftID = this.getLiftID().equals(other.getLiftID());
		}
		return sameLiftID && this.compare(other);
	}

}
