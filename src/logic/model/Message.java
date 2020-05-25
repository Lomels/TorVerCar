package logic.model;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Message {

	private String content;
	private User from;

	public Message(String message, User from) throws InvalidInputException {
		this.setContent(message);
		this.setFrom(from);
	}

	public void setContent(String message) throws InvalidInputException {
		InputChecker.checkGeneric(message);
		this.content = message;
	}

	public void setFrom(User from) throws InvalidInputException {
		if (from == null) {
			throw new InvalidInputException("User must not be null");
		}
		this.from = from;
	}

	public String getContent() {
		return this.content;
	}

	public User getFrom() {
		return this.from;
	}
}
