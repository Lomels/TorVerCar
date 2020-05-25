package logic.model;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Report {

	private String message;
	private StudentCar target;
	private Boolean resolved;
	private Admin handler;

	public Report(String message, StudentCar target, Admin handler) throws InvalidInputException {
		this.setMessage(message);
		this.setTarget(target);
		this.resolved = false;
		this.setHandler(handler);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) throws InvalidInputException {
		InputChecker.checkGeneric(message);
		this.message = message;
	}

	public StudentCar getTarget() {
		return target;
	}

	public void setTarget(StudentCar target) throws InvalidInputException {
		if (target == null) {
			throw new InvalidInputException("Target must not be null");
		} else {
			this.target = target;
		}
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public Admin getHandler() {
		return handler;
	}

	public void setHandler(Admin handler) throws InvalidInputException {
		if (handler == null) {
			throw new InvalidInputException("Handler must not be null");
		} else {
			this.handler = handler;
		}
	}

}
