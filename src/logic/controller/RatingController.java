package logic.controller;

import logic.view.mysql.MySqlDAO;

public class RatingController {

	private static MySqlDAO ourDb = new MySqlDAO();
	
	public static void upvote(String userID, Integer liftID, String driverID) {
		ourDb.upvoteRating(userID, liftID, driverID);
	}
	
	public static void downvote(String userID, Integer liftID, String driverID) {
		ourDb.downvoteRating(userID, liftID, driverID);
	}
}
