package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

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
	public void existingUser() throws DatabaseException, InvalidInputException {
		MySqlDAO my = new MySqlDAO();
		assertTrue(my.existByUserID(USERID));
	}

//	@Test
	public void notExistingUser() throws DatabaseException, InvalidInputException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.existByUserID(NOT_EX_USERID));
	}

//	@Test
	public void banned() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertTrue(my.wasBannedByUserID(BANNED));
	}

//	@Test
	public void notBanned() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.wasBannedByUserID(USERID));
	}

//	@Test
	public void notExistingBanned() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.wasBannedByUserID(NOT_EX_USERID));
	}

//	@Test
	public void loadNonExistingStudent() {
		MySqlDAO my = new MySqlDAO();
		assertThrows(DatabaseException.class, () -> my.loadStudentByUserID(NOT_EX_USERID));
	}

//	@Test
	public void editInfo() throws Exception {
		// TODO: implementare
	}

//	@Test
	public void existingPassword() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		String actualP;
		actualP = my.loadPasswordByUserID(USERID);
		assertEquals(PASSWORD, actualP);
	}
	
//	@Test
	public void updateRating() throws DatabaseException, InvalidInputException {
		MySqlDAO my = new MySqlDAO();
		RatingController ratController = new RatingController();
		StudentCar test = my.loadStudentCarByUserID("0000000");
		MyLogger.info("Before test rating", test.getRating());
		
		ratController.upvoteLift("0000001", 1, my.loadStudentCarByUserID("0000000"));
		test = my.loadStudentCarByUserID("0000000");
		MyLogger.info("After upvote test", test.getRating());
		
		ratController.downvote("00000010", 2, my.loadStudentCarByUserID("0000000"));
		test = my.loadStudentCarByUserID("0000000");
		MyLogger.info("After downvote test", test.getRating());

	}

}
