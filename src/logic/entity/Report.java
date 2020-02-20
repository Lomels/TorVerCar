package logic.entity;

import java.security.InvalidParameterException;

public class Report {
	private String message;
	private StudentCar target;
	private Boolean resolved;
	private Admin handler;

	public Report(String message, StudentCar target, Admin handler) {
		this.setMessage(message);
		this.setTarget(target);
		this.resolved = false;
		this.setHandler(handler);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(message == null || message.length() == 0) {
			throw new InvalidParameterException("Message must not be empty or null");
		} else {
			this.message = message;
		}
	}

	public StudentCar getTarget() {
		return target;
	}

	public void setTarget(StudentCar target) {
		if(target == null) {
			throw new InvalidParameterException("Must pass a valid StudentCar");
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

	public void setHandler(Admin handler) {
		if(handler == null) {
			throw new InvalidParameterException("Must pass a valid Admin");
		} else {
			this.handler = handler;
		}
	}

}
