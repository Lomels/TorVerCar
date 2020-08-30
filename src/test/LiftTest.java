package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import test.utilities.TestUtilities;

public class LiftTest extends TestUtilities {

	Integer liftID;
	LocalDateTime startDateTime;
	Integer maxDuration;
	String actualNote = NOTE;
	StudentCar driver;
	List<Student> passengers;
	Route route;

	Lift dummyLift;

	public LiftTest() throws InvalidInputException, DatabaseException {
		liftID = null;
		startDateTime = LocalDateTime.parse(START_DATE_TIME_EARLY);
		route = Route.jsonDecode(new JSONObject(R_MARCO_UNI));
		maxDuration = (int) (route.getTotalDuration() * 1.5);
		driver = dao.loadStudentCarByUserID(generateUserID(1));
		passengers = new ArrayList<>();
		passengers.add(dao.loadStudentByUserID(generateUserID(DRIVER_NUMBER + 1)));
		passengers.add(dao.loadStudentByUserID(generateUserID(DRIVER_NUMBER + 2)));
		dummyLift = getDummyLift();
	}

	@Test
	public void LiftCompareTrue() throws InvalidInputException {
		Lift lift1 = new Lift(liftID, startDateTime, maxDuration, actualNote, driver, passengers, route);
		Lift lift2 = new Lift(1, startDateTime, maxDuration, actualNote, driver, passengers, route);

		assertTrue(lift1.compare(lift2));
	}

	@Test
	public void LiftCompareFalse() throws InvalidInputException {
		Lift lift1 = new Lift(liftID, startDateTime, maxDuration, actualNote, driver, passengers, route);
		Lift lift2 = new Lift(1, startDateTime, maxDuration, actualNote, driver, null, route);

		assertFalse(lift1.compare(lift2));
	}

	@Test
	public void LiftCompareDifferentPassengers() throws InvalidInputException, DatabaseException {
		Lift lift1 = new Lift(liftID, startDateTime, maxDuration, actualNote, driver, passengers, route);
		List<Student> passengers2 = new ArrayList<>(passengers);
		passengers2.remove(1);
		passengers2.add(dao.loadStudentByUserID(generateUserID(DRIVER_NUMBER + 3)));
		Lift lift2 = new Lift(1, startDateTime, maxDuration, actualNote, driver, passengers2, route);

		assertFalse(lift1.compare(lift2));
	}

	@Test
	public void compareWithIDCorrect() throws DatabaseException, InvalidInputException {
		Lift lift1 = new Lift(liftID, startDateTime, maxDuration, actualNote, driver, passengers, route);
		Lift lift2 = new Lift(liftID, startDateTime, maxDuration, actualNote, driver, passengers, route);

		assertTrue(lift1.compareWithID(lift2));
	}

	@Test
	public void compareWithIDOneNull() throws DatabaseException, InvalidInputException {
		Lift lift1 = new Lift(liftID, startDateTime, maxDuration, actualNote, driver, passengers, route);
		Lift lift2 = dao.loadLiftByID(1);

		assertFalse(lift1.compareWithID(lift2));
	}

	@Test
	public void compareWithIDDifferent() throws DatabaseException, InvalidInputException {
		Lift lift1 = dao.loadLiftByID(1);
		Lift lift2 = dao.loadLiftByID(2);

		assertFalse(lift1.compareWithID(lift2));
	}

	@Test
	public void setMaxDurationExpection() {
		assertThrows(InvalidInputException.class, () -> dummyLift.setMaxDuration(-1));
	}

	@Test
	public void isPassengerWithNull() {
		assertFalse(dummyLift.isPassenger(null));
	}

	@Test
	public void isPassengerCorrect() throws InvalidInputException, DatabaseException {
		Lift lift = getDummyLift();
		Student student = getDummyStudent();
		lift.getPassengers().add(student);

		assertTrue(lift.isPassenger(student));
	}
	
	@Test
	public void isPassengerIncorrect() throws InvalidInputException, DatabaseException {
		Lift lift = new Lift(liftID, startDateTime, maxDuration, actualNote, driver, null, route);
		Student student = getDummyStudent();
		
		assertFalse(lift.isPassenger(student));
	}

}
