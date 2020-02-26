package logic.entity;

import java.util.List;

import logic.controller.exception.InvalidInputException;

public class Lift {
	
	//TODO: implement better
	private String liftID;
	private StudentCar driver;
	private String note;
	private Route route;
	private List<Message> messages;
	
	public Lift(String liftID, String note, Route route, StudentCar driver) {
		this.liftID = liftID;
		this.note = note;
		this.route = route;
		this.driver = driver;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void addMessage(Message messages) throws InvalidInputException {
		if(messages == null) {
			throw new InvalidInputException("Message must not be null");
		}
		this.messages.add(messages);
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
	
	
}
