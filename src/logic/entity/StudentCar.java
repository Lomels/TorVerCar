package entity;

import java.util.List;

import bean.CarInfoBean;

public class StudentCar extends Student {

	private int rating;
	private CarInfo carInfo;
	private List<Report> reports;

	public boolean isAvailable(Route route) {
		// TODO da implementare
		return true;
	}
	
	//TODO Usato in setCarInfoController, da mettere in una factory
	//Da implementare
	public StudentCar(Student student, CarInfoBean carInfoBean) {
		super(student.userID, student.password, student.name, student.surname);
	}

	public StudentCar(String userID, String password, String name, String surname, Integer rating, CarInfo carInfo,
			List<Report> reports) {
		super(userID, password, name, surname);
		this.rating = rating;
		this.carInfo = carInfo;
		this.reports = reports;
	}

	public void updateRating(int vote) {
		if((vote != 1) || (vote != -1)) {
			// TODO Implementare meglio
			return;
		}
		this.rating += vote;
	}

	public int getRating() {
		return rating;
	}
	
/* TODO - HA SENSO METTERLO? PER ME NO - Marco
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
			// TODO Implementare meglio
			return;
		}
		this.carInfo = carInfo;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void addReport(Report report) {
		if(report == null) {
			// TODO Implementare meglio
			return;
		}
		(this.reports).add(report);
	}

	public void removeReport(Report report) {
		(this.reports).remove(report);
	}
	
}
