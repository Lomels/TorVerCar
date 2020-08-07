package logic.model;

import java.util.*;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Admin {

	private String adminID;
	private String email;
	private String password;
	private ArrayList<Report> handledReports;

	protected Admin(String id, String password) throws InvalidInputException {
		this.setAdminID(id);
		this.setPassword(password);
	}

	public String getAdminID() {
		return this.adminID;
	}

	public void setAdminID(String id) throws InvalidInputException {
		InputChecker.checkUserID(id);
		this.adminID = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws InvalidInputException {
		InputChecker.checkPassword(password);
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) throws InvalidInputException {
		InputChecker.checkEmail(email);
		this.email = email;
	}

	public void addReport(Report report) throws InvalidInputException {
		InputChecker.checkNotNull(report, "Report");
		this.handledReports.add(report);
	}

}
