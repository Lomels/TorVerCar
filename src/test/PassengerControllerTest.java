package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.junit.Test;

import logic.controller.PassengerController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Lift;
import logic.model.Route;
import logic.model.Student;
import test.utilities.TestUtilities;

public class PassengerControllerTest extends TestUtilities {

	private static final PassengerController PASSENGER_CONTROLLER = new PassengerController();

	public PassengerControllerTest() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void addPassengerTestCorrect() throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();
		Integer liftID = 7;

		Student passenger = addStudentToDB();
		Lift lift = dao.loadLiftByID(liftID);
		assert (lift.getPassengers().isEmpty());

		PASSENGER_CONTROLLER.addPassenger(lift, passenger);
		dbModified();

		Lift fromDBLift = dao.loadLiftByID(liftID);

		boolean isAppUpdated = lift.getPassengers().size() == 1 && lift.getPassengers().get(0).compare(passenger);
		assertTrue(isAppUpdated);

		boolean isDBUpdated = fromDBLift.getPassengers().size() == 1
				&& fromDBLift.getPassengers().get(0).compare(passenger);
		assertTrue(isDBUpdated);
	}

	@Test
	public void addPassengerTestAlreadyPassenger()
			throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();
		Integer liftID = 7;

		Student passenger = addStudentToDB();
		Lift lift = dao.loadLiftByID(liftID);
		assert (lift.getPassengers().isEmpty());

		PASSENGER_CONTROLLER.addPassenger(lift, passenger);
		dbModified();

		assertThrows(InvalidStateException.class, () -> PASSENGER_CONTROLLER.addPassenger(lift, passenger));
	}

	@Test
	public void addPassengerTestDriver() throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();
		Integer liftID = 7;

		Lift lift = dao.loadLiftByID(liftID);
		assert (lift.getPassengers().isEmpty());
		Student passenger = dao.loadStudentByUserID(lift.getDriver().getUserID());

		assertThrows(InvalidStateException.class, () -> PASSENGER_CONTROLLER.addPassenger(lift, passenger));
	}

	@Test
	public void addPassengerTestFull() throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();
		Integer liftID = 8;

		Lift lift = dao.loadLiftByID(liftID);
		assert (lift.getPassengers().size() == 4);
		Student passenger = addStudentToDB();

		assertThrows(InvalidStateException.class, () -> PASSENGER_CONTROLLER.addPassenger(lift, passenger));
	}

	@Test
	public void addPassengerTestNullID() throws InvalidInputException, DatabaseException {
		Lift lift = new Lift(null, LocalDateTime.parse(START_DATE_TIME_EARLY), 500, null, getDummyDriver(), null,
				Route.jsonDecode(new JSONObject(R_MARCO_UNI)));

		Student passenger = getDummyStudent();

		assertThrows(InvalidStateException.class, () -> PASSENGER_CONTROLLER.addPassenger(lift, passenger));
	}

	@Test
	public void addPassengerTestAlreadyBooked() throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();
		Integer liftID1 = 7;
		Integer liftID2 = 5;

		Lift lift1 = dao.loadLiftByID(liftID1);
		Lift lift2 = dao.loadLiftByID(liftID2);
		assert (lift1.getPassengers().isEmpty());
		assert (lift2.getPassengers().isEmpty());
		Student passenger = addStudentToDB();
		PASSENGER_CONTROLLER.addPassenger(lift1, passenger);
		assertThrows(InvalidStateException.class, () -> PASSENGER_CONTROLLER.addPassenger(lift2, passenger));
	}

	@Test
	public void removePassengerTest() throws DatabaseException, InvalidInputException {
		populateDB();
		Integer liftID = 7;

		Lift lift = dao.loadLiftByID(liftID);
		assert (lift.getPassengers().isEmpty());

		Student passenger = addStudentToDB();

		assertThrows(InvalidStateException.class, () -> PASSENGER_CONTROLLER.removePassenger(lift, passenger));
	}
}
