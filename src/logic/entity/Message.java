package logic.entity;

import logic.controller.InputChecker;
import logic.controller.exception.InvalidInputException;

public class Message {

	private String message;
	private User from;

	public Message(String message, User from) throws InvalidInputException {
		this.setMessage(message);
		this.setFrom(from);
	}

	public void setMessage(String message) throws InvalidInputException {
		InputChecker.checkGeneric(message);
		this.message = message;
	}

	public void setFrom(User from) throws InvalidInputException {
		if (from == null) {
			throw new InvalidInputException("User must not be null");
		}
		this.from = from;
	}

	public String getMessage() {
		return this.message;
	}

	public User getFrom() {
		return this.from;
	}
}
