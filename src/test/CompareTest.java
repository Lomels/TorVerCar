package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import test.utilities.TestUtilities;

public class CompareTest extends TestUtilities {

	Integer liftID;
	LocalDateTime startDateTime;
	Integer maxDuration;
	String note;
	StudentCar driver;
	List<Student> passengers;
	Route route;

	public CompareTest() throws JSONException, InvalidInputException, DatabaseException {
		liftID = null;
		startDateTime = LocalDateTime.parse(START_DATE_TIME_EARLY);
		route = Route.jsonDecode(new JSONObject(R_MARCO_UNI));
		maxDuration = (int) (route.getDuration() * 1.5);
		note = NOTE;
		driver = dao.loadStudentCarByUserID(USER_ID + "1");
		passengers = new ArrayList<>();
		passengers.add(dao.loadStudentByUserID(USER_ID + (DRIVER_NUMBER + 1)));
		passengers.add(dao.loadStudentByUserID(USER_ID + (DRIVER_NUMBER + 2)));
	}

	@Test
	public void LiftCompareTrue() throws InvalidInputException{
		Lift lift1 = new Lift(liftID, startDateTime, maxDuration, note, driver, passengers, route);
		Lift lift2 = new Lift(1, startDateTime, maxDuration, note, driver, passengers, route);

		assertTrue(lift1.compare(lift2));
	}
	
	@Test
	public void LiftCompareFalse() throws InvalidInputException{
		Lift lift1 = new Lift(liftID, startDateTime, maxDuration, note, driver, passengers, route);
		Lift lift2 = new Lift(1, startDateTime, maxDuration, note, driver, null, route);
		
		assertFalse(lift1.compare(lift2));
	}

}
