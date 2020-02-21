package logic.controller;

import java.util.List;

import logic.controller.exception.InvalidInputException;
import logic.entity.Lift;
import logic.entity.Profile;
import logic.entity.Student;
import logic.entity.WeeklyPreferencies;

public class StudentBuilder{

	//Matriculation number, used for login
	protected String userID;
	//Stored encrypted
	protected String password;
	protected String name;
	protected String surname;
	protected Profile profile;
	//Collection of favorite routes for the week
	protected WeeklyPreferencies weeklyPreferencies;
	//Collection of active lifts
	protected List<Lift> lifts;

	//Costruttore del builder
	public StudentBuilder(String userID) throws InvalidInputException{
		try {
			InputChecker.checkUserID(userID);
			this.userID = userID;
		} catch (Exception e) {
			throw e;
		}
	}

	public static StudentBuilder newBuilder(String userID) throws InvalidInputException{
		return new StudentBuilder(userID);
	}

	//Metodo che chiama correttamente il costruttore di Student
	public Student build() {
		return new Student(this.userID, this.password, this.name, this.surname, this.profile, this.weeklyPreferencies, this.lifts);
	}

	/*
	 *	Tutti i metodi necessari per l'utilizzo del pattern 
	 */
	public StudentBuilder password(String password) {
		this.password = password;
		return this;
	}

	public StudentBuilder fullname(String name, String surname) {
		this.name = name;
		this.surname = surname;
		return this;
	}

	public StudentBuilder profile(Profile profile) {
		this.profile = profile;
		return this;
	}

	public StudentBuilder weeklyPreferencies(WeeklyPreferencies weekly) {
		this.weeklyPreferencies = weekly;
		return this;
	}

	public StudentBuilder lifts(List<Lift> lifts) {
		this.lifts = lifts;
		return this;
	}


}