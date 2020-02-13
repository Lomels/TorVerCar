package logic.controller;

import java.util.ArrayList;

import logic.controller.StudentBuilder;
import logic.controller.StudentCarBuilder;
import logic.entity.CarInfo;
import logic.entity.Lift;
import logic.entity.Profile;
import logic.entity.Report;
import logic.entity.Student;
import logic.entity.StudentCar;
import logic.entity.WeeklyPreferencies;

public class StudentCarBuilder extends StudentBuilder{
	
	private String userID;
	private int rating;
	private CarInfo carInfo;
	private ArrayList<Report> reports;
	private Student student;

	public StudentCarBuilder(String userID, Student student) {
		super(userID);
		this.student = student;
	}

	public static StudentCarBuilder newBuilder(String userID) {
		return new StudentCarBuilder(userID, null);
	}
	
	public static StudentCarBuilder newBuilder(Student student) {
		return new StudentCarBuilder(student.getUserID(), student);
	}

	
	public StudentCar build() {
		if(this.student == null) {
			return new StudentCar(this.userID, this.password, this.name, this.surname, this.profile, this.weeklyPreferencies, this.lifts, this.rating, this.carInfo, this.reports);
		} else {
			return new StudentCar(this.student, this.rating, this.carInfo, this.reports);
		}
	}

	/*
	 *	Tutti i metodi necessari per l'utilizzo del pattern 
	 */
	@Override
	public StudentCarBuilder password(String password) {
		this.password = password;
		return this;
	}

	@Override
	public StudentCarBuilder fullname(String name, String surname) {
		this.name = name;
		this.surname = surname;
		return this;
	}
	
	@Override
	public StudentCarBuilder profile(Profile profile) {
		this.profile = profile;
		return this;
	}

	@Override
	public StudentCarBuilder weeklyPreferencies(WeeklyPreferencies weekly) {
		this.weeklyPreferencies = weekly;
		return this;
	}
	
	@Override
	public StudentCarBuilder lifts(ArrayList<Lift> lifts) {
		this.lifts = lifts;
		return this;
	}
	
	public StudentCarBuilder student(Student student) {
		this.student = student;
		return this;
	}

	public StudentCarBuilder rating(int rating) {
		this.rating = rating;
		return this;
	}

	public StudentCarBuilder carInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
		return this;
	}

	public StudentCarBuilder reports(ArrayList<Report> reports) {
		this.reports = reports;
		return this;
	}

}
