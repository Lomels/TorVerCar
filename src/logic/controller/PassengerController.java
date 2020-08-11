package logic.controller;

import logic.model.Lift;
import logic.model.Student;
import logic.view.mysql.MySqlDAO;

public class PassengerController {

	public void addPassenger(Lift lift, Student student) {
		// Add the passenger at the application level
		lift.getPassengers().add(student);

		// Add the passenger at persitence level
		MySqlDAO dao = new MySqlDAO();

		// TODO
	}

}
