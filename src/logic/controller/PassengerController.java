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
		// The operation is blocked
		if(lift.getLiftID() == null) {
			// If the lift is not saved on the DB
			String errorMessage = "Lift has null liftID, save on DB before adding a passenger.";
			throw new InvalidStateException(errorMessage);
		}
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
		} else {
			for (Lift passengerLift : dao.listLiftsByPassengerID(passenger.getUserID())) {
				// If passenger has already a passage in the same time
				if (!this.liftInteresct(lift, passengerLift)) {
					String errorMessage = "Passenger has already a lift booked for this time.";
					throw new InvalidStateException(errorMessage);
				}
			}
			// Add the passenger at the application level
			lift.getPassengers().add(passenger);
			// Add the passenger to the persistence and update the lift
			dao.addPassengerByLiftIDAndUserID(lift.getLiftID(), passenger.getUserID());
			dao.saveLift(lift);
		}
	}

	public void removePassenger(Lift lift, Student student) throws DatabaseException, InvalidStateException {
		boolean removed = false;
		
		for (Student s : lift.getPassengers()) {
			if (student.getUserID().contentEquals(s.getUserID())) {
				lift.getPassengers().remove(s);
				removed = true;
				break;
			}
		}

		if (!removed) {
			String message = String.format("Student with userID: %s, is not a passenger of lift: %d.",
					student.getUserID(), lift.getLiftID());
			throw new InvalidStateException(message);
		}

		dao.removePassengerByLiftIDAndUserID(lift.getLiftID(), student.getUserID());
		dao.saveLift(lift);

		String subject = "Your lift has changed!";
		String format = "The student %s has deleted his booking for the lift departing at: %s.";
		String message = String.format(format, student.getUserID(), lift.getStartDateTime());

		String[] recipients = new String[] { lift.getDriver().getEmail() };
		new SendEmail().send(recipients, recipients, subject, message);

		dao.addNotificationByUserID(lift.getDriver().getUserID(), message);
	}

	private boolean liftInteresct(Lift newLift, Lift passengerLift) {
		boolean stopsBefore = passengerLift.getStopDateTime().isBefore(newLift.getStartDateTime());
		boolean startAfter = passengerLift.getStartDateTime().isAfter(newLift.getStopDateTime());
		return stopsBefore || startAfter;
	}

}
