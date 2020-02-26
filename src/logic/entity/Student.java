package logic.entity;

import java.util.List;

import logic.controller.InputChecker;
import logic.controller.exception.InvalidInputException;

public class Student extends User {

	protected String name;
	protected String surname;
	protected Profile profile;
	// Collection of favorite routes for the week
	protected WeeklyPreferencies weeklyPreferencies;
	// Collection of active lifts
	protected List<Lift> lifts;

	// Generated
	public Student(String userID, String password, String name, String surname, Profile profile,
			WeeklyPreferencies weeklyPreferencies, List<Lift> lifts) throws InvalidInputException {
		super(userID, password);
		this.setName(name);
		this.setSurname(surname);
		this.profile = profile;
		this.weeklyPreferencies = weeklyPreferencies;
		this.lifts = lifts;
	}

	// Costruttore che parte dello studentCar
	public Student(StudentCar studentCar) throws InvalidInputException {
		this(studentCar.userID, studentCar.password, studentCar.name, studentCar.surname, studentCar.profile,
				studentCar.weeklyPreferencies, studentCar.lifts);
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

	public void setProfile(Profile profile) throws InvalidInputException {
		this.profile = profile;
	}

	public WeeklyPreferencies getWeeklyPreferencies() {
		return weeklyPreferencies;
	}

	public void setWeeklyPreferencies(WeeklyPreferencies weeklyPreferencies) throws InvalidInputException {
		this.weeklyPreferencies = weeklyPreferencies;
	}

	public List<Lift> getLifts() {
		return this.lifts;
	}

	public void setLifts(List<Lift> lifts) throws InvalidInputException {
		this.lifts = lifts;
	}

	// Generato automaticamente
	@Override
	public String toString() {
		return "Student [name=" + name + ", surname=" + surname + ", profile=" + profile + ", weeklyPreferencies="
				+ weeklyPreferencies + ", lifts=" + lifts + "]";
	}

}
