package logic.entity;

import java.util.ArrayList;

import logic.entity.CarInfo;
import logic.entity.Report;
import logic.entity.Route;
import logic.entity.Student;

public class StudentCar extends Student {

	private int rating;
	private CarInfo carInfo;
	private ArrayList<Report> reports;

	//Costruttore che usa lo student
	public StudentCar(Student student, int rating, CarInfo carInfo, ArrayList<Report> reports) {
		super(student.userID, student.password, student.name, student.surname, student.profile, student.weeklyPreferencies, student.lifts);
		this.rating = rating;
		this.carInfo = carInfo;
		this.reports = reports;
	}

	public boolean isAvailable(Route route) {
		return route == null;
	}

	public void updateRating(int vote) {
		if((vote != 1) || (vote != -1)) {
			return;
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

	public void setCarInfo(CarInfo carInfo) {
		if(carInfo == null) {
			return;
		}
		this.carInfo = carInfo;
	}

	public ArrayList<Report> getReports() {
		return reports;
	}

	public void addReport(Report report) {
		if(report == null) {
			return;
		}
		(this.reports).add(report);
	}

	public void removeReport(Report report) {
		(this.reports).remove(report);
	}

	@Override
	public String toString() {
		return super.toString()  + ", " + "StudentCar [rating=" + rating + ", carInfo=" + carInfo + ", reports=" + reports + "]";
	}



}
