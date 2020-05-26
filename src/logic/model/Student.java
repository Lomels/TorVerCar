package logic.model;

import java.util.List;

import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.utilities.InputChecker;

public class Student{

	protected String userID;
	protected String password;
	protected String name;
	protected String surname;
	protected String email;
	protected Profile profile;
	// Collection of favorite routes for the week
	protected WeeklyPreferencies weeklyPreferencies;
	// Collection of active lifts
	protected List<Lift> lifts;
	
	protected Boolean state;

	// Generated
	public Student(String userID, String password, String email, String name, String surname, Profile profile,
			WeeklyPreferencies weeklyPreferencies, List<Lift> lifts) throws InvalidInputException {
		this.userID = userID;
		this.password = password;
		this.setName(name);
		this.setSurname(surname);
		this.profile = profile;
		this.weeklyPreferencies = weeklyPreferencies;
		this.lifts = lifts;
		this.email = email;
	}

	// Costruttore che parte dello studentCar
	public Student(StudentCar studentCar) throws InvalidInputException {
		this(studentCar.userID, studentCar.password, studentCar.email, studentCar.name, studentCar.surname, studentCar.profile,
				studentCar.weeklyPreferencies, studentCar.lifts);
	}

	public void setUserID(String id) {
		this.userID = id;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) throws InvalidInputException {
		InputChecker.checkPassword(password);
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws InvalidInputException{
		InputChecker.checkEmail(email);
		this.email = email;	
	}
	
	public String getName() {
		return this.name;
	}

	// Name must not be null
	public void setName(String name) throws InvalidInputException {
		InputChecker.checkGeneric(name);
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	// Surname must not be null
	public void setSurname(String surname) throws InvalidInputException {
		InputChecker.checkGeneric(surname);
		this.surname = surname;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public WeeklyPreferencies getWeeklyPreferencies() {
		return weeklyPreferencies;
	}

	public void setWeeklyPreferencies(WeeklyPreferencies weeklyPreferencies) {
		this.weeklyPreferencies = weeklyPreferencies;
	}

	public List<Lift> getLifts() {
		return this.lifts;
	}

	public void setLifts(List<Lift> lifts) {
		this.lifts = lifts;
	}

	// Generato automaticamente
	@Override
	public String toString() {
		return "Student [name=" + name + ", surname=" + surname + ", profile=" + profile + ", weeklyPreferencies="
				+ weeklyPreferencies + ", lifts=" + lifts + "]";
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

}
