package logic.model;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class StudentCar extends Student {

	private int rating;
	private CarInfo carInfo;

	// Constructor from Student
	public StudentCar(Student student, int rating, CarInfo carInfo) throws InvalidInputException {
		super(student.userID, student.password, student.email, student.name, student.surname, student.phone);
		this.rating = rating;
		this.setCarInfo(carInfo);
	}

	public void updateRating(int vote) throws InvalidInputException {
		if ((vote != 1) || (vote != -1)) {
			throw new InvalidInputException("Vote must be 1 or -1");
		}
		this.rating += vote;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public CarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CarInfo carInfo) throws InvalidInputException {
		InputChecker.checkNotNull(carInfo, "CarInfo");
		this.carInfo = carInfo;
	}

	@Override
	public String toString() {
		return super.toString() + ", " + "StudentCar [rating=" + rating + ", carInfo=" + carInfo + "]";
	}

}
