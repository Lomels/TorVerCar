package logic.model;

import java.util.List;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Student {

	protected String userID;
	protected String password;
	protected String name;
	protected String surname;
	protected String email;
	protected String phone;
	
	// used only in webapp
	protected List<Lift> bookedLift;
	protected List<String> notifications;


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

	
	public void setName(String name)  {
		InputChecker.checkNotNull(name, "Name");
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname)  {
		InputChecker.checkNotNull(surname, "Surname");
		this.surname = surname;
	}
	
	public String getFullName() {
		return this.name +" "+ this.surname;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) throws InvalidInputException {
		InputChecker.checkPhone(phone);
		this.phone = phone;
	}


	public List<Lift> getBookedLift() {
		return bookedLift;
	}

	public void setBookedLift(List<Lift> bookedLift) {
		this.bookedLift = bookedLift;
	}

	
	@Override
	public String toString() {
		return "Student [userID=" + userID + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", phone=" + phone + "]";
	}
	
	public List<String> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<String> notifications) {
		this.notifications = notifications;
	}
	
	public boolean compare(Student other) {
		boolean sameUserID = this.getUserID().equals(other.getUserID());
		boolean samePassword = this.getPassword().equals(other.getPassword());
		boolean sameName = this.getName().equals(other.getName());
		boolean sameSurname = this.getSurname().equals(other.getSurname());
		boolean sameEmail = this.getEmail().equals(other.getEmail());
		boolean samePhone = this.getPhone().equals(other.getPhone());
		return sameUserID && samePassword && sameName && sameSurname && sameEmail && samePhone;
	}

}
