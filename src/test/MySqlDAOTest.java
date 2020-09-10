package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import logic.bean.LiftBean;
import logic.bean.UserBean;
import logic.controller.RatingController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.StudentCar;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

public class MySqlDAOTest {

	private static final String USERID = "0000000";
	private static final String BANNED = "1234567";
	private static final String PASSWORD = "aaaAAA123@";
	private static final String NOT_EX_USERID = "1000";

//	@Test
	public void existingUser() throws DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertTrue(my.existByUserID(USERID));
	}

//	@Test
	public void notExistingUser() throws DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.existByUserID(NOT_EX_USERID));
	}

//	@Test
	public void banned() throws DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertTrue(my.wasBannedByUserID(BANNED));
	}

//	@Test
	public void notBanned() throws DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.wasBannedByUserID(USERID));
	}

//	@Test
	public void notExistingBanned() throws DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.wasBannedByUserID(NOT_EX_USERID));
	}

//	@Test
	public void loadNonExistingStudent() {
		MySqlDAO my = new MySqlDAO();
		assertThrows(DatabaseException.class, () -> my.loadStudentByUserID(NOT_EX_USERID));
	}

//	@Test
	public void editInfo() {
		// TODO: implementare
	}

//	@Test
	public void existingPassword() throws DatabaseException {
		MySqlDAO my = new MySqlDAO();
		String actualP;
		actualP = my.loadPasswordByUserID(USERID);
		assertEquals(PASSWORD, actualP);
	}
  
//	@Test
	public void updateRating() throws DatabaseException, InvalidInputException {
		MySqlDAO my = new MySqlDAO();
		StudentCar test = my.loadStudentCarByUserID(USERID);
		MyLogger.info("Before test rating", test.getRating());
		
		RatingController rate = new RatingController();
		LiftBean lift = new LiftBean();
		UserBean user = new UserBean();
		
		lift.setLiftID(1);
		user.setUserID("0000001");
		
		rate.upvoteLift(user, lift);
		test = my.loadStudentCarByUserID(USERID);
		MyLogger.info("After upvote test", test.getRating());
		
		lift.setLiftID(2);
		user.setUserID("00000010");
		rate.downvote(user, lift);
		test = my.loadStudentCarByUserID(USERID);
		MyLogger.info("After downvote test", test.getRating());

	}

}
