package logic.controller;

import java.util.ArrayList;

import logic.controller.StudentBuilder;
import logic.controller.StudentCarBuilder;
import logic.entity.CarInfo;
import logic.entity.Report;
import logic.entity.Student;
import logic.entity.StudentCar;

public class StudentCarBuilder extends StudentBuilder{

	private int rating;
	private CarInfo carInfo;
	private ArrayList<Report> reports;
	private Student student;

	public StudentCarBuilder(String userID, Student student) {
		super(userID);
		this.student = student;
	}

	public static StudentCarBuilder newBuilder(Student student) {
		return new StudentCarBuilder(student.getUserID(), student);
	}

	@Override
	public StudentCar build() {
		return new StudentCar(this.student, this.rating, this.carInfo, this.reports);
	}

	
	//Tutti i metodi necessari per l'utilizzo del pattern 
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
