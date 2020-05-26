package logic.model;

import java.util.*;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Admin{
	//TODO: Implement class
	
	private String adminID;
	private String email;
	private String password;
	private ArrayList<Report> handledReports;
	
	protected Admin(String userID, String password) throws InvalidInputException {
		this.adminID = userID;
		this.password = password;
	}
	
	public String getAdminID() {
		return this.adminID;
	}
	
	public void setAdminID(String id) {
		this.adminID = id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws InvalidInputException {
		InputChecker.checkPassword(password);
		this.password = password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void addReport(Report report) {
		this.handledReports.add(report);
	}
	
	
	

}

