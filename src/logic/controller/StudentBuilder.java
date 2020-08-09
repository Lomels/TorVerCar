package logic.controller;

import java.util.List;

import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.Student;
import logic.utilities.InputChecker;

public class StudentBuilder {

	// Matriculation number, used for login
	protected String userID;
	// Stored encrypted
	protected String password;
	protected String name;
	protected String surname;
	protected String phone;
	protected String email;
	// Collection of active lifts
	protected List<Lift> lifts;

	// Costruttore del builder
	public StudentBuilder(String userID) throws InvalidInputException {
		InputChecker.checkUserID(userID);
		this.userID = userID;
	}

	public static StudentBuilder newBuilder(String userID) throws InvalidInputException {
		return new StudentBuilder(userID);
	}

	// Metodo che chiama correttamente il costruttore di Student
	public Student build() throws InvalidInputException {
		return new Student(this.userID, this.password, this.email, this.name, this.surname, this.phone);
	}

	/*
	 * Tutti i metodi necessari per l'utilizzo del pattern
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

	public StudentBuilder phone(String phone) {
		this.phone = phone;
		return this;
	}

	public StudentBuilder lifts(List<Lift> lifts) {
		this.lifts = lifts;
		return this;
	}

	public StudentBuilder email(String email) {
		this.email = email;
		return this;
	}

}