package logic.controller;

import logic.controller.email.SendEmail;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidStateException;
import logic.model.Lift;
import logic.model.Student;
import logic.view.mysql.MySqlDAO;

public class PassengerController {
	private MySqlDAO dao = new MySqlDAO();

	public void addPassenger(Lift lift, Student passenger) throws InvalidStateException, DatabaseException {
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
		} // TODO: If passenger has already a passage in the same time
		else {
			// Add the passenger at the application level
			lift.getPassengers().add(passenger);
			// Add the passenger to the persistence and update the lift
			dao.addPassengerByLiftIDAndUserID(lift.getLiftID(), passenger.getUserID());
			dao.saveLift(lift);
		}
	}

	public void removePassenger(Lift lift, Student student) throws DatabaseException {
		for (Student s : lift.getPassengers()) {
			if (student.getUserID().contentEquals(s.getUserID())) {
				lift.getPassengers().remove(s);
				break;
			}
		}

		dao.removePassengerByLiftIDAndUserID(lift.getLiftID(), student.getUserID());
		dao.saveLift(lift);
		
		String subject = "Your lift has changed!";
		String format = "The student %s has deleted his booking for the lift departing at: %s.";
		String message = String.format(format, student.getUserID(), lift.getStartDateTime());

		String[] recipients = new String[] { lift.getDriver().getEmail() };
		new SendEmail().send(recipients, recipients, subject, message);

		dao.addNotificationByUserID(lift.getDriver().getUserID(), message);

		// TODO: test
	}

}
