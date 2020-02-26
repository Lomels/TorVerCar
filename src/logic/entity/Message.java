package logic.entity;

import java.security.InvalidParameterException;

// Il setUser e setMessage probabilmente sono inutili (?)

public class Message {
	//TODO: change Exception

	private String myMessage;
	private User from;
	
	public Message(String message, User from) {
		this.myMessage = message;
		this.from = from;
	}
	
	public void setMessage(String message) {
		if(message.length() <= 0) {
			throw new InvalidParameterException("Message lenght must be greater than 0");
		}else {
			this.myMessage = message;
		}
	}
	
	public String getMessage() {
		return this.myMessage;
	}
	
	public User getUser() {
		return this.from;
	}
}
