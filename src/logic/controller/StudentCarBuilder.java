package logic.controller;

import java.security.InvalidParameterException;

import logic.controller.exception.InvalidInputException;
import logic.model.CarInfo;
import logic.model.Student;
import logic.model.StudentCar;

public class StudentCarBuilder {

	private int rating;
	private CarInfo carInfo;
	private Student student;

	private StudentCarBuilder(Student student){
		this.student = student;
	}

	public static StudentCarBuilder newCarBuilder(Student student) throws InvalidInputException {
		if (student == null) {
			throw new InvalidParameterException("Student must not be null");
		}
		return new StudentCarBuilder(student);
	}

	public StudentCar build() throws InvalidInputException {
		return new StudentCar(this.student, this.rating, this.carInfo);
	}

	// Tutti i metodi necessari per l'utilizzo del pattern
	public StudentCarBuilder rating(int rating) {
		this.rating = rating;
		return this;
	}

	public StudentCarBuilder carInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
		return this;
	}

}
