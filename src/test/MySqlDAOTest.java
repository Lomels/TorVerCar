package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.view.mysql.MySqlDAO;

public class MySqlDAOTest {

	private static final String USERID = "12345";
	private static final String PASSWORD = "aaaaa1@";
	private static final String NOT_EX_USERID = "1000";

	@Test
	public void existingUser() throws DatabaseException, InvalidInputException {
		MySqlDAO my = new MySqlDAO();
		assertTrue(my.existByUserID(USERID));
	}

	@Test
	public void notExistingUser() throws DatabaseException, InvalidInputException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.existByUserID(NOT_EX_USERID));
	}

	@Test
	public void banned() throws InvalidInputException, DatabaseException {
		String userID = "123456";
		MySqlDAO my = new MySqlDAO();
		assertTrue(my.wasBannedByUserID(userID));
	}

	@Test
	public void notBanned() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.wasBannedByUserID(USERID));
	}

	@Test
	public void notExistingBanned() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.wasBannedByUserID(NOT_EX_USERID));
	}

	@Test
	public void loadNonExistingStudent() {
		MySqlDAO my = new MySqlDAO();
		assertThrows(DatabaseException.class, () -> my.loadStudentByUserID(NOT_EX_USERID));
	}

	@Test
	public void editInfo() throws Exception {
		// TODO: implementare
	}

	@Test
	public void existingPassword() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		String actualP;
		actualP = my.loadPasswordByUserID(USERID);
		assertEquals(PASSWORD, actualP);
	}

}
