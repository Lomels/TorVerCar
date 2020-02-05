package Entity;

import Entity.User;
import java.util.ArrayList;

public class Admin extends User {
	private String adminID;
	private String email;
	private ArrayList<Report> handledReports;
	
	protected Admin(String userID, String password) {
		super(userID, password);
		// TODO Auto-generated constructor stub
	}
	
	public String getAdminID() {
		return this.adminID;
	}
	
	public void setAdminID(String ID) {
		this.adminID = ID;
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

