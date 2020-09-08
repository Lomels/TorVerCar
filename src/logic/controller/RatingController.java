package logic.controller;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;

public class RatingController {

	private static final MySqlDAO ourDb = new MySqlDAO();

	public void upvoteLift(String passengerID, Integer liftID, StudentCar driver)
			throws InvalidInputException, DatabaseException {
		// Update in the application level
		driver.updateRating(1);
		// Update in the DB level
		ourDb.upvoteRating(passengerID, liftID, driver.getUserID());
		// Delete lift if concluded
		this.deleteLiftIfConcluded(liftID);
	}

	//TODO: me sa che passare -1 è n'attimo illegale
	public void downvote(String passengerID, Integer liftID, StudentCar driver)
			throws InvalidInputException, DatabaseException {
		// Update in the application level
		driver.updateRating(-1);
		// Update in the DB level
		ourDb.downvoteRating(passengerID, liftID, driver.getUserID());
		// Delete lift if concluded
		this.deleteLiftIfConcluded(liftID);
	}

	private void deleteLiftIfConcluded(Integer liftID) throws DatabaseException, InvalidInputException {
		Lift lift = ourDb.loadLiftByID(liftID);
		LiftController liftController = new LiftController();
		liftController.deleteLiftIfConcluded(lift);
	}
}
