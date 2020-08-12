package logic.controller;

import logic.controller.exception.InvalidStateException;
import logic.model.Lift;
import logic.model.Student;
import logic.view.mysql.MySqlDAO;

public class PassengerController {

	public void addPassenger(Lift lift, Student passenger) throws InvalidStateException {
		// Add the student only if was not already added and it's not the driver and
		// there are available seats
		if (lift.isPassenger(passenger)) {
			// If student is already a passenger
			String errorMessage = "Passenger: " + passenger.getUserID() + " was already added.";
			throw new InvalidStateException(errorMessage);
		} else if (passenger.getUserID().equals(lift.getDriver().getUserID())) {
			// If student is the driver
			String errorMessage = "Passenger: " + passenger.getUserID() + " is the driver.";
			throw new InvalidStateException(errorMessage);
		} else if (lift.getFreeSeats() <= 0) {
			// If lift has no free seats.
			String errorMessage = "Lift has no more free seats";
			throw new InvalidStateException(errorMessage);
		} else if (false) {
			// If passenger has already a passage in the same time
			// TODO
		} else {
			// Add the passenger at the application level
			lift.getPassengers().add(passenger);
			// Add the passenger to the persistence
			MySqlDAO dao = new MySqlDAO();
			dao.addPassengerByLiftIDAndUserID(lift.getLiftID(), passenger.getUserID());
		}
	}

}
