package logic.controller;

import logic.bean.LiftBean;
import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.view.mysql.MySqlDAO;

public class RatingController {

	private static final MySqlDAO ourDb = new MySqlDAO();

	public void upvoteLift(UserBean user, LiftBean lift)
			throws InvalidInputException, DatabaseException {
		// Update in the application level
		lift.getDriver().updateRating(1);
		// Update in the DB level
		ourDb.upvoteRating(user.getUserID(), lift.getLiftID(), lift.getDriver().getUserID());
		// Delete lift if concluded
		this.deleteLiftIfConcluded(lift.getLiftID());
	}

	public void downvote(UserBean user, LiftBean lift)
			throws InvalidInputException, DatabaseException {
		// Update in the application level
		lift.getDriver().updateRating(-1);
		// Update in the DB level
		ourDb.downvoteRating(user.getUserID(), lift.getLiftID(), lift.getDriver().getUserID());
		// Delete lift if concluded
		this.deleteLiftIfConcluded(lift.getLiftID());
	}

	private void deleteLiftIfConcluded(Integer liftID) throws DatabaseException, InvalidInputException {
		Lift lift = ourDb.loadLiftByID(liftID);
		LiftController liftController = new LiftController();
		liftController.deleteLiftIfConcluded(lift);
	}
}
