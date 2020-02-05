package Entity;

import java.util.ArrayList;

public class Lift {
	private String liftID;
	private StudentCar driver;
	private String note;
	private Route route;
	private ArrayList<Message> messages;
	
	public Lift(String liftID, String note, Route route, StudentCar driver) {
		this.liftID = liftID;
		this.note = note;
		this.route = route;
		this.driver = driver;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void addMessage(Message messages) {
		if(messages == null) {
			return;
		}
		(this.messages).add(messages);
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
