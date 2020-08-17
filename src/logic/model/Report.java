package logic.model;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Report {

	private String message;
	private StudentCar target;
	private Student from;
	private Admin handler;
	private Boolean resolved;

	public Report(Student from, StudentCar target, String message, Boolean resolved, Admin handler)
			throws InvalidInputException {
		super();
		this.message = message;
		this.setTarget(target);
		this.setFrom(from);
		this.setResolved(resolved);
		this.setHandler(handler);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StudentCar getTarget() {
		return target;
	}

	public void setTarget(StudentCar target) throws InvalidInputException {
		InputChecker.checkNotNull(target, "Target");
		this.target = target;
	}

	public Student getFrom() {
		return from;
	}

	public void setFrom(Student from) throws InvalidInputException {
		InputChecker.checkNotNull(from, "From");
		this.from = from;
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) throws InvalidInputException {
		InputChecker.checkNotNull(resolved, "Resolved");
		this.resolved = resolved;
	}

	public Admin getHandler() {
		return handler;
	}

	public void setHandler(Admin handler) {
		this.handler = handler;
	}

}
