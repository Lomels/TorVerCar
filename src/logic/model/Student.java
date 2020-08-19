package logic.model;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Student {

	protected String userID;
	protected String password;
	protected String name;
	protected String surname;
	protected String email;
	protected String phone;

	// Generated
	public Student(String userID, String password, String email, String name, String surname, String phone)
			throws InvalidInputException {
		this.setUserID(userID);
		this.setPassword(password);
		this.setName(name);
		this.setSurname(surname);
		this.setPhone(phone);
		this.setEmail(email);
	}

	// Constructor from StudentCar
	public Student(StudentCar studentCar) throws InvalidInputException {
		this(studentCar.userID, studentCar.password, studentCar.email, studentCar.name, studentCar.surname,
				studentCar.phone);
	}

	public void setUserID(String id) throws InvalidInputException {
		InputChecker.checkUserID(id);
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

	public void setEmail(String email) throws InvalidInputException {
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

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) throws InvalidInputException {
		InputChecker.checkPhone(phone);
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Student [userID=" + userID + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", phone=" + phone + "]";
	}

}
