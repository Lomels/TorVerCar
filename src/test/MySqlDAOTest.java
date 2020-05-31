package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import logic.controller.StudentBuilder;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Student;
import logic.view.mysql.MySqlDAO;

public class MySqlDAOTest {

	private static final String USERID = "12345";
	private static final String PASSWORD = "aaaaa1@";
	private static final String NAME = "Mario";
	private static final String SURNAME = "Rossi";
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
	public void loadExistingStudent() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		Student sByDao = my.loadStudentByUserID(USERID);
		Student sToCompare = StudentBuilder.newBuilder(USERID).password(PASSWORD).fullname(NAME, SURNAME).build();
		// TODO: Change to sByDao.equals()
		assertEquals(sByDao.toString(), sToCompare.toString());
	}

	@Test
	public void loadNonExistingStudent() {
		MySqlDAO my = new MySqlDAO();
		assertThrows(DatabaseException.class, () -> my.loadStudentByUserID(NOT_EX_USERID));
	}

	@Test
	public void addStudent() throws DatabaseException, InvalidInputException {
		MySqlDAO my = new MySqlDAO();
		
		Integer id = Integer.valueOf(1);
		// id incrementale per test
		do {
			id++;
		} while (my.existByUserID(id.toString()));
		String userID = id.toString();
		
		String password = "bbbbb2@";
		String name = "Luigi";
		String surname = "Bianchi";

		Student s = StudentBuilder.newBuilder(userID).password(password).fullname(name, surname).build();
		my.addStudent(s);
		Student sByDao = my.loadStudentByUserID(userID);
		assertEquals(s.toString(), sByDao.toString());
	}
	
	@Test
	public void editInfo() throws Exception{
		//TODO: implementare
	}

	@Test
	public void existingPassword() throws InvalidInputException, DatabaseException {
		MySqlDAO my = new MySqlDAO();
		String actualP;
		actualP = my.loadPasswordByUserID(USERID);
		assertEquals(PASSWORD, actualP);
	}

}
