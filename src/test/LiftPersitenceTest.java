package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.controller.LiftController;
import logic.controller.PassengerController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.exception.PassengerException;
import logic.model.Lift;
import logic.model.Student;
import test.utilities.TestUtilities;

public class LiftPersitenceTest extends TestUtilities {

	private void setup() {
		populateDB();
	}

	@Test
	public void saveNewLift() throws InvalidInputException, DatabaseException {
		this.setup();

		Lift lift = getDummyLift();

		dao.saveLift(lift);
		dbModified();

		Lift fromDB = dao.getLastInsertedLift();

		assertTrue(lift.compare(fromDB));
	}


	@Test
	public void loadLiftByID() throws DatabaseException, InvalidInputException {
		this.setup();
		Integer lastID = dao.getLastInsertedLiftID();
		Lift liftByID = dao.loadLiftByID(lastID);
		Lift lastInsertedLift = dao.getLastInsertedLift();

		assertTrue(liftByID.compare(lastInsertedLift));
	}

//	@Test
	public void deleteLiftByID() throws DatabaseException, InvalidInputException {
		this.setup();

		LiftController controller = new LiftController();
		Integer liftIDToRemove = dao.getLastInsertedLiftID();
		Lift lift = dao.loadLiftByID(liftIDToRemove);
		controller.deleteLift(lift);
		dbModified();

		assertThrows(DatabaseException.class, () -> dao.loadLiftByID(liftIDToRemove));
	}

	@Test
	public void updateLift() throws DatabaseException, InvalidInputException {
		this.setup();

		Integer liftIDToRetrieve = dao.getLastInsertedLiftID();
		Lift lift = dao.loadLiftByID(liftIDToRetrieve);

		Integer startingMaxDuration = lift.getMaxDuration();
		Integer finalMaxDuration = startingMaxDuration * 10;

		lift.setMaxDuration(finalMaxDuration);
		dao.saveLift(lift);
		dbModified();

		Lift secondLift = dao.loadLiftByID(liftIDToRetrieve);
		assertEquals(finalMaxDuration, secondLift.getMaxDuration());
	}


	@Test
	public void listPassenger()
			throws DatabaseException, InvalidInputException, InvalidStateException, PassengerException {
		this.setup();
		PassengerController passengerController = new PassengerController();
		Lift newLift = getDummyLift();
		dao.saveLift(newLift);
		dbModified();
		newLift = dao.getLastInsertedLift();

		List<Student> passengers = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			Student newStudent = addStudentToDB();
			passengers.add(newStudent);
			passengerController.addPassenger(newLift, newStudent);
		}

		List<Student> passengersFromDB = dao.listPassengersByLiftID(newLift.getLiftID());
		boolean samePassengers = true;
		try {
			for (int index = 0; index < passengers.size(); index++) {
				if (!passengers.get(index).compare(passengersFromDB.get(index)))
					samePassengers = false;
			}
		} catch (Exception e) {
			samePassengers = false;
		} finally {
			assertTrue(samePassengers);
		}
	}

	@Test
	public void removePassenger()
			throws InvalidInputException, DatabaseException, InvalidStateException, PassengerException {
		this.setup();
		PassengerController passengerController = new PassengerController();

		Lift newLift = getDummyLift();

		dao.saveLift(newLift);

		newLift = dao.getLastInsertedLift();

		Student student1 = addStudentToDB();
		Student student2 = addStudentToDB();

		passengerController.addPassenger(newLift, student1);
		passengerController.addPassenger(newLift, student2);

		passengerController.removePassenger(newLift, student2);

		Lift liftFromDb = dao.getLastInsertedLift();

		List<Student> passengersInApp = newLift.getPassengers();
		boolean conditionInApp = passengersInApp.size() == 1 && passengersInApp.get(0).compare(student1);

		List<Student> passengersInDB = liftFromDb.getPassengers();
		boolean conditionInDB = passengersInDB.size() == 1 && passengersInDB.get(0).compare(student1);

		assertTrue(conditionInApp && conditionInDB);

	}

}
