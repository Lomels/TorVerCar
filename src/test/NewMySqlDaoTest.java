package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.CarInfo;
import logic.model.Lift;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.OurStudentDatabase;
import logic.view.mysql.MySqlDAO;
import test.utilities.TestLogger;
import test.utilities.TestUtilities;

public class NewMySqlDaoTest extends TestUtilities {

	private static final OurStudentDatabase dao = new MySqlDAO();
	private static final TestLogger LOGGER = new TestLogger(NewMySqlDaoTest.class.getCanonicalName());

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

//	@Test
	public void saveLift() throws InvalidInputException, DatabaseException {
		populateDB();
		Lift lift = getDummyLift();
		dao.saveLift(lift);
		Lift fromDBLift = dao.getLastInsertedLift();
		assertTrue(lift.compare(fromDBLift));
	}

//	@Test
	public void addCar() throws InvalidInputException, DatabaseException {
		emptyDB();
		String userID = "0252379";
		Student student = new Student(userID, "aaaAAA123@", "g.marseglia@lll.it", "Giuseppe", "MARSA", "1234567890");
		CarInfo carInfo = new CarInfo("BB123BB", 4, "model", "color");
		StudentCar driver = new StudentCar(student, 0, carInfo);
		dao.addStudent(student);
		dao.addCar(driver);
		StudentCar driverFromDB = dao.loadStudentCarByUserID(userID);
		LOGGER.info("driverFromDB", driverFromDB);
		assertTrue(driver.compare(driverFromDB));
	}

	@Test
	public void addStudentCar() throws InvalidInputException, DatabaseException {
		emptyDB();
		String userID = "0252379";
		Student student = new Student(userID, "aaaAAA123@", "g.marseglia@lll.it", "Giuseppe", "MARSA", "1234567890");
		CarInfo carInfo = new CarInfo("BB123BB", 4, "model", "color");
		StudentCar driver = new StudentCar(student, 0, carInfo);
		dao.addStudentCar(driver);
		StudentCar driverFromDB = dao.loadStudentCarByUserID(userID);
		LOGGER.info("driverFromDB", driverFromDB);
		assertTrue(driver.compare(driverFromDB));
	}

}
