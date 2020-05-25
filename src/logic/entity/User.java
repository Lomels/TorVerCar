package logic.entity;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;
import logic.controller.exception.*;
public class User {

	// Matriculation number, used for login
	protected String userID;

	// Stored encrypted
	protected String password;

	protected Boolean state;
	
	// Only child classes can use this method(?)
	protected User(String userID, String password) throws InvalidInputException {
		this.setUserID(userID);
		this.setPassword(password);
	}
	
	protected User(String userID, String password, Boolean state) throws InvalidInputException, InvalidStateException {
		this.setUserID(userID);
		this.setPassword(password);
		this.setState(state);
	}

	public Boolean getState() {
		return state;
	}
	
	public void setState(Boolean newState) throws InvalidStateException{
		if(this.state.equals(newState)&&newState) {
			throw new InvalidStateException("User already logged in.");
		}else if(this.state.equals(newState) && !newState) {
			throw new InvalidStateException("User already logged out.");
		}else {
			this.state = newState;
		}
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
