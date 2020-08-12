package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import logic.controller.PassengerController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

public class LiftPersitenceTest {

	private MySqlDAO dao = new MySqlDAO();

	private static final String MARCO_ID = "0241118";
	private static final String GIULIA_ID = "0245061";
	private static final String GIUSEPPE_ID = "0252379";

//	@Test
	public void saveLiftWithoutPassengers() throws InvalidInputException, DatabaseException {

		String fromData = "{\"duration\":33,\"distance\":27083,\"stops\":[{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";

		Integer liftID = null;
		LocalDateTime startDateTime = LocalDateTime.now();
		Integer maxDuration = 200;
		String note = "Ma non so cosa ce nella mia pelle bianca";
		StudentCar driver = dao.loadStudentCarByUserID(MARCO_ID);
		List<Student> passengers = null;
		Route route = Route.JSONdecode(new JSONObject(fromData));

		Lift lift = new Lift(liftID, startDateTime, maxDuration, note, driver, passengers, route);

		MyLogger.info("Lift to save for the first time", lift);

		dao.saveLift(lift);
	}

//	@Test
	public void loadLift() throws JSONException, DatabaseException, InvalidInputException {
		Integer liftIDtoRetrieve = 5;
		Lift lift = dao.loadLiftByID(liftIDtoRetrieve);
		MyLogger.info("lift", lift);
	}

//	@Test
	public void deleteLiftByID() {
		Integer liftIDToRemove = 4;
		dao.deleteLiftByID(liftIDToRemove);
	}

//	@Test
	public void updateLift() throws JSONException, DatabaseException, InvalidInputException {
		Integer liftIDToRetrieve = 5;
		Lift lift = dao.loadLiftByID(liftIDToRetrieve);
		MyLogger.info("lift first retrieve", lift);

		lift.setMaxDuration(lift.getMaxDuration() * 10);
		MyLogger.info("lift after duration modified", lift);
		dao.saveLift(lift);

		Lift secondLift = dao.loadLiftByID(liftIDToRetrieve);
		MyLogger.info("lift second retrieve", secondLift);
	}

//	@Test
	public void listLiftAfter() {
		LocalDateTime startDateTime = LocalDateTime.parse("2020-08-10T16:00:00");
		List<Lift> results = dao.listLiftStartingAfterDateTime(startDateTime);
		for (Lift lift : results) {
			MyLogger.info("One of the result", lift);
		}
	}
	
//	@Test
	public void listAvailableListAfter() {
		LocalDateTime startDateTime = LocalDateTime.parse("2020-08-10T15:00:00");
		List<Lift> results = dao.listAvailableLiftStartingAfterDateTime(startDateTime);
		for(Lift lift : results) {
			MyLogger.info("One of the result", lift);
		}
	}

//	@Test
	public void listLiftBefore() {
		LocalDateTime stopDateTime = LocalDateTime.parse("2020-08-10T16:00:00");
		List<Lift> results = dao.listLiftStoppingBeforeDateTime(stopDateTime);
		for (Lift lift : results) {
			MyLogger.info(lift.getStopDateTime().toString());
		}
	}
	
//	@Test
	public void listAvailableLiftBefore() {
		LocalDateTime stopDateTime = LocalDateTime.parse("2020-08-10T16:00:00");
		List<Lift> results = dao.listLiftStoppingBeforeDateTime(stopDateTime);
		for (Lift lift : results) {
			MyLogger.info(lift.getStopDateTime().toString());
		}
	}

//	@Test
	public void addPassenger() throws DatabaseException, InvalidInputException {
		Integer liftID = 7;

		Student passenger = dao.loadStudentByUserID(GIUSEPPE_ID);
		MyLogger.info("Passenger", passenger);

		Lift lift = dao.loadLiftByID(liftID);
		MyLogger.info("lift", lift);

		PassengerController pc = new PassengerController();

		try {
			dao.addPassengerByLiftIDAndUserID(liftID, GIULIA_ID);
			pc.addPassenger(lift, passenger);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

//	@Test
	public void listPassenger() throws DatabaseException, InvalidInputException {
		Integer liftID = 7;

		List<Student> passengers = dao.listPassengersByLiftID(liftID);

		for (Student p : passengers) {
			MyLogger.info("One of the passenger", p);
		}
	}

//	@Test
	public void removePassenger() throws DatabaseException, InvalidInputException {
		Integer liftID = 7;

		List<Student> passengers = new ArrayList<Student>();
		try {
			passengers = dao.listPassengersByLiftID(liftID);
			for (Student p : passengers) {
				MyLogger.info("One of the passenger", p);
			}
			Student first = passengers.get(0);

			dao.removePassengerByLiftIDAndUserID(liftID, first.getUserID());

			MyLogger.info("Removed: " + first.getUserID());

			passengers = dao.listPassengersByLiftID(liftID);

			for (Student pNew : passengers) {
				MyLogger.info("One of the new passenger", pNew);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
