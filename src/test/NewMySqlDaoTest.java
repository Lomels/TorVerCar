package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.Student;
import logic.view.OurStudentDatabase;
import logic.view.mysql.MySqlDAO;
import test.utilities.TestUtilities;

public class NewMySqlDaoTest extends TestUtilities {

	private static final OurStudentDatabase dao = new MySqlDAO();

//	@Test
	public void existByUserID() throws DatabaseException {
		populateDB();
		String userID = "0000000";
		assertTrue(dao.existByUserID(userID));
	}

//	@Test
	public void addStudent() throws InvalidInputException, DatabaseException {
		emptyDB();
		String userID = "0252379";
		Student student = new Student(userID, "aaaAAA123@", "g.marseglia@lll.it", "Giuseppe", "MARSA", "1234567890");
		dao.addStudent(student);
		assertTrue(dao.existByUserID(userID));
	}

//	@Test
	public void loadStudent() throws InvalidInputException, DatabaseException {
		emptyDB();
		String userID = "0252379";
		Student student = new Student(userID, "aaaAAA123@", "g.marseglia@lll.it", "Giuseppe", "MARSA", "1234567890");
		dao.addStudent(student);
		Student fromDBStudent = dao.loadStudentByUserID(userID);
		assertTrue(student.compare(fromDBStudent));
	}

	@Test
	public void saveLift() throws InvalidInputException, DatabaseException {
		populateDB();
		Lift lift = getDummyLift();
		dao.saveLift(lift);
		Lift fromDBLift = dao.getLastInsertedLift();
		assertTrue(lift.compare(fromDBLift));
	}

}
