package Entity;

// Il setUser e setMessage probabilmente sono inutili (?)

public class Message {
	private String message;
	private User from;
	
	public Message(String message, User from) {
		this.message = message;
		this.from = from;
	}
	
	public void setMessage(String message) {
		if(message.length() == 0) {
			return;
		}else {
			this.message = message;
		}
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public User getUser() {
		return this.from;
	}
}
