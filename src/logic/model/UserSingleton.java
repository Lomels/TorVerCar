package logic.model;

import java.util.List;

import logic.controller.exception.WrongStatus;
import logic.utilities.Status;

public class UserSingleton {

	private Student student = null;
	private StudentCar studentCar = null;
	private List<String> notifications;
	private List<Lift> completedLift;

	private Role role;
	private Status status;

	private String code;

	private static UserSingleton instance = null;

	private UserSingleton() {
	}

	public static UserSingleton getInstance() {
		if (instance == null)
			instance = new UserSingleton();
		return instance;
	}

	public String getUserID() {
		if (role.equals(Role.STUDENT))
			return this.student.getUserID();
		if (role.equals(Role.DRIVER))
			return this.studentCar.getUserID();
		else
			return null;
	}

	public void setStudent(Student user) {
		this.student = user;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudentCar(StudentCar user) {
		this.studentCar = user;
	}

	public StudentCar getStudentCar() {
		return this.studentCar;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		if (status.equals(Status.OFFER) || status.equals(Status.BOOK))
			this.status = status;
		else
			throw new WrongStatus();
	}

	public List<String> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<String> notifications) {
		this.notifications = notifications;
	}

	public List<Lift> getCompletedLift() {
		return completedLift;
	}

	public void setCompletedLift(List<Lift> completedLift) {
		this.completedLift = completedLift;
	}

	public void clearState() {
		this.setCode(null);
		this.completedLift.clear();
		this.setRole(null);
		this.setStatus(null);
		this.setStudent(null);
		this.setStudentCar(null);
		this.notifications.clear();
	}
}
