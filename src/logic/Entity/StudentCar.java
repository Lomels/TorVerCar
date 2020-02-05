package Entity;

import java.util.ArrayList;
import Bean.CarInfoBean;

public class StudentCar extends Student {

	private int rating;
	private CarInfo carInfo;
	private ArrayList<Report> reports;

	public boolean isAvailable(Route route) {
		return true;
	}
	
	//Usato in setCarInfoController, da mettere in una factory
	//Da implementare
	public StudentCar(Student student, CarInfoBean carInfoBean) {
		super(student.userID, student.password, student.name, student.surname);
	}

	//generated
	public StudentCar(String userID, String password, String name, String surname, int rating, CarInfo carInfo,
			ArrayList<Report> reports) {
		super(userID, password, name, surname);
		this.rating = rating;
		this.carInfo = carInfo;
		this.reports = reports;
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
	
/* HA SENSO METTERLO? PER ME NO - Marco
 * 
	public void setRating(int rating) {
		this.rating = rating;
	}
*/
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
	
}
