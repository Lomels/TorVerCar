package logic.model;

import java.util.*;

import logic.controller.exception.InvalidInputException;

public class Admin extends User {
	//TODO: Implement class
	
	private String adminID;
	private String email;
	private ArrayList<Report> handledReports;
	
	protected Admin(String userID, String password) throws InvalidInputException {
		super(userID, password);
	}
	
	public String getAdminID() {
		return this.adminID;
	}
	
	public void setAdminID(String id) {
		this.adminID = id;
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

