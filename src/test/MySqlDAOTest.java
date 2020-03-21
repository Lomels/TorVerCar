package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import logic.controller.StudentBuilder;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.entity.Student;
import logic.view.mysql.MySqlDAO;

public class MySqlDAOTest {

	@Test
	public void existingUser() throws DatabaseException, InvalidInputException {
		String userID = "12345";
		MySqlDAO my = new MySqlDAO();
		assertTrue(my.existByUserID(userID));
	}

	@Test
	public void notExistingUser() throws DatabaseException, InvalidInputException {
		String userID = "1000";
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.existByUserID(userID));
	}

	@Test
	public void banned() throws InvalidInputException, DatabaseException {
		String userID = "123456";
		MySqlDAO my = new MySqlDAO();
		assertTrue(my.wasBannedByUserID(userID));
	}

	@Test
	public void notBanned() throws InvalidInputException, DatabaseException {
		String userID = "12345";
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.wasBannedByUserID(userID));
	}
	
	@Test
	public void notExistingBanned() throws InvalidInputException, DatabaseException {
		String userID = "1000";
		MySqlDAO my = new MySqlDAO();
		assertFalse(my.wasBannedByUserID(userID));
	}
	
	@Test
	public void loadExistingStudent() throws InvalidInputException, DatabaseException {
		String userID = "12345";
		MySqlDAO my = new MySqlDAO();
		Student sByDao = my.loadStudentByUserID(userID);
		Student sToCompare = StudentBuilder.newBuilder(userID).password("aaaaa1@").fullname("Mario", "Rossi").build();
		//assertTrue(sByDao.equals(sToCompare));
		//TODO: Change to equals
		assertEquals(sByDao.toString(), sToCompare.toString());
	}
	
	@Test
	public void loadNonExistingStudent() {
		String userID = "1000";
		MySqlDAO my = new MySqlDAO();
		assertThrows(DatabaseException.class, () -> my.loadStudentByUserID(userID));
	}
	
	@Test
	public void addStudent() throws DatabaseException, InvalidInputException {
		MySqlDAO my = new MySqlDAO();
		Integer id = new Integer(1);
		//id incrementale per test
		do {
			id++;
		}while(my.existByUserID(id.toString()));
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
	public void existingPassword() throws InvalidInputException, DatabaseException {
		String userID = "12345";
		MySqlDAO my = new MySqlDAO();
		String expectedP = "aaaaa1@";
		String actualP;
		actualP = my.loadPasswordByUserID(userID);
		assertEquals(expectedP, actualP);
	}
	
	
}
