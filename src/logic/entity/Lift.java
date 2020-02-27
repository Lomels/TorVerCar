package logic.entity;

import java.util.List;

import logic.controller.InputChecker;
import logic.controller.exception.InvalidInputException;

public class Lift {
	private String liftID;
	private StudentCar driver;
	private String note;
	private Route route;
	private List<Message> messages;

	public Lift(String liftID, String note, Route route, StudentCar driver) throws InvalidInputException {
		this.setLiftID(liftID);
		this.setNote(note);
		this.setRoute(route);
		this.setDriver(driver);
	}

	public String getLiftID() {
		return liftID;
	}

	public StudentCar getDriver() {
		return driver;
	}

	public String getNote() {
		return note;
	}

	public Route getRoute() {
		return route;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setLiftID(String liftID) throws InvalidInputException {
		InputChecker.checkGeneric(liftID);
		this.liftID = liftID;
	}

	public void setDriver(StudentCar driver) throws InvalidInputException {
		if (driver == null) {
			throw new InvalidInputException("Driver must not be null");
		}
		this.driver = driver;
	}

	public void setNote(String note) throws InvalidInputException {
		InputChecker.checkGeneric(note);
		this.note = note;
	}

	public void setRoute(Route route) throws InvalidInputException {
		if (route == null) {
			throw new InvalidInputException("Route must not be null");
		}
		this.route = route;
	}

	public void addMessage(Message message) throws InvalidInputException {
		if (message == null) {
			throw new InvalidInputException("Message must not be null");
		}
		this.messages.add(message);
	}

}
