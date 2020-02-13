package logic.entity;

// Il setUser e setMessage probabilmente sono inutili (?)

public class Message {
	private String myMessage;
	private User from;
	
	public Message(String message, User from) {
		this.myMessage = message;
		this.from = from;
	}
	
	public void setMessage(String message) {
		if(message.length() == 0) {
			// TODO Implementare meglio
			return;
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
