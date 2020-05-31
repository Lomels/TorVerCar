package logic.model;

import java.util.List;

import logic.controller.exception.InvalidInputException;

public class StudentCar extends Student {

	private int rating;
	private CarInfo carInfo;
	private List<Report> reports;

	// Costruttore che usa lo student
	public StudentCar(Student student, int rating, CarInfo carInfo, List<Report> reports) throws InvalidInputException {
		super(student.userID, student.password, student.email, student.name, student.surname, student.phone,
				student.lifts);
		this.rating = rating;
		this.setCarInfo(carInfo);
		this.reports = reports;
	}

	public boolean isAvailable(Route route) {
		return route == null;
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
		if (carInfo == null) {
			throw new InvalidInputException("CarInfo must not be null");
		}
		this.carInfo = carInfo;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void addReport(Report report) throws InvalidInputException {
		if (report == null) {
			throw new InvalidInputException("Report must not be null");
		}
		this.reports.add(report);
	}

	public void removeReport(Report report) {
		this.reports.remove(report);
	}

	@Override
	public String toString() {
		return super.toString() + ", " + "StudentCar [rating=" + rating + ", carInfo=" + carInfo + ", reports="
				+ reports + "]";
	}

}
