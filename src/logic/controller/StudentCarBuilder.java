package logic.controller;

import java.security.InvalidParameterException;
import java.util.List;

import logic.controller.exception.InvalidInputException;
import logic.entity.CarInfo;
import logic.entity.Report;
import logic.entity.Student;
import logic.entity.StudentCar;

public class StudentCarBuilder extends StudentBuilder{

	private int rating;
	private CarInfo carInfo;
	private List<Report> reports;
	private Student student;

	public StudentCarBuilder(Student student) throws InvalidInputException {
		super(student.getUserID());
		this.student = student;
	}

	public static StudentCarBuilder newCarBuilder(Student student) throws InvalidInputException{
		if(student == null) {
			throw new InvalidParameterException("Student must not be null");
		}
		return new StudentCarBuilder(student);
	}

	@Override
	public StudentCar build() {
		return new StudentCar(this.student, this.rating, this.carInfo, this.reports);
	}
	
	//Tutti i metodi necessari per l'utilizzo del pattern 
	public StudentCarBuilder rating(int rating) {
		this.rating = rating;
		return this;
	}

	public StudentCarBuilder carInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
		return this;
	}

	public StudentCarBuilder reports(List<Report> reports) {
		this.reports = reports;
		return this;
	}

}
