package logic.entity;

import logic.controller.InputChecker;
import logic.controller.exception.InvalidInputException;

public class User {

	// Matriculation number, used for login
	protected String userID;

	// Stored encrypted
	protected String password;

	// Only child classes can use this method(?)
	protected User(String userID, String password) throws InvalidInputException {
		this.setUserID(userID);
		this.setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws InvalidInputException {
		InputChecker.checkPassword(password);
		this.password = password;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) throws InvalidInputException {
		InputChecker.checkUserID(userID);
		this.userID = userID;
	}

}
