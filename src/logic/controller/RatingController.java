package logic.controller;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Lift;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;

public class RatingController {

	private static final MySqlDAO ourDb = new MySqlDAO();

	public void upvoteLift(String passengerID, Integer liftID, StudentCar driver)
			throws InvalidInputException, DatabaseException, InvalidStateException {
		this.voteLift(passengerID, liftID, driver, true);
	}

	public void downvoteLift(String passengerID, Integer liftID, StudentCar driver)
			throws InvalidInputException, DatabaseException, InvalidStateException {
		this.voteLift(passengerID, liftID, driver, false);
	}

	private void deleteLiftIfConcluded(Integer liftID) throws DatabaseException, InvalidInputException {
		Lift lift = ourDb.loadLiftByID(liftID);
		LiftController liftController = new LiftController();
		liftController.deleteLiftIfConcluded(lift);
	}

	private void voteLift(String passengerID, Integer liftID, StudentCar driver, boolean upvote)
			throws DatabaseException, InvalidInputException, InvalidStateException {
		Student passenger = ourDb.loadStudentByUserID(passengerID);
		Lift lift = ourDb.loadLiftByID(liftID);
		if (lift.isPassenger(passenger)) {
			// First update in application level, then update in database level
			if (upvote) {
				driver.updateRating(1);
				ourDb.upvoteRating(passengerID, liftID, driver.getUserID());
			} else {
				driver.updateRating(-1);
				ourDb.downvoteRating(passengerID, liftID, driver.getUserID());
			}
			this.deleteLiftIfConcluded(liftID);
		} else {
			String message = String.format("Student: %s is not a passenger of lift: %d.", passengerID, liftID);
			throw new InvalidStateException(message);
		}
	}
}
