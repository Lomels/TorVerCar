package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import logic.controller.LiftController;
import logic.controller.PassengerController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Lift;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

public class LiftPersitenceTest extends TestUtilities{

	private MySqlDAO dao = new MySqlDAO();
	private LiftController liftController = new LiftController();
	private PassengerController passController = new PassengerController();
	private Route route;


	private static final String MARCO_ID = "0241118";
	private static final String GIULIA_ID = "0245061";
	private static final String GIUSEPPE_ID = "0252379";

	private static final MapsApi MAPS_API = AdapterMapsApi.getInstance();

	private static final String ADDRESS_1 = "Via Prenestina Nuova 51, Palestrina";
	private static final String ADDRESS_2 = "Via Folcarotonda 19, Palestrina";

	private void setup() throws InvalidInputException {
		populateDB();

		Route routeForStops = Route.jsonDecode(new JSONObject(R_MARCO_GIU_TIVOLI_UNI));
		List<Position> routeStops = routeForStops.getStops();

		List<Position> stops = new ArrayList<>();
		stops.add(routeStops.get(1));
		stops.add(routeStops.get(2));

		this.route = maps.startToStop(stops);
		MyLogger.info("Route of the passenger: " + this.route.toStringLong());
	}
	
	@Test
	public void saveLiftWithoutPassengers() throws InvalidInputException, DatabaseException {

		String fromData = "{\"duration\":33,\"distance\":27083,\"stops\":[{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";

		Integer liftID = 5;
		LocalDateTime startDateTime = LocalDateTime.now();
		Integer maxDuration = 200;
		String note = "Ma non so cosa ce nella mia pelle bianca";
		StudentCar driver = dao.loadStudentCarByUserID(MARCO_ID);
		List<Student> passengers = null;
		Route route = Route.jsonDecode(new JSONObject(fromData));

		Lift lift = new Lift(liftID, startDateTime, maxDuration, note, driver, passengers, route);

		MyLogger.info("Lift to save for the first time", lift);

		dao.saveLift(lift);
	}
	
	@Test
	public void createLift() throws InvalidInputException, DatabaseException, ApiNotReachableException {
		String startDateTimeString = LocalDateTime.now().toString();
		Integer maxDuration = 200;
		String note = "createLift test";
		StudentCar driver = dao.loadStudentCarByUserID("0000000");
		
		Position pickup = MAPS_API.addrToPos(ADDRESS_2).get(0);
		Position dropoff = MAPS_API.addrToPos(ADDRESS_1).get(0);
		
		Lift lift = liftController.createLift(null, startDateTimeString, maxDuration, note, driver, null, pickup, dropoff);
		MyLogger.info("Created Lift: ", lift);
			
	}

	@Test
	public void loadLift() throws JSONException, DatabaseException, InvalidInputException {
		Integer liftIDtoRetrieve = 5;
		Lift lift = dao.loadLiftByID(liftIDtoRetrieve);
		MyLogger.info("lift", lift);
	}

	@Test
	public void deleteLiftByID() throws JSONException, DatabaseException, InvalidInputException {
		LiftController controller = new LiftController();
		Integer liftIDToRemove = 5;
		Lift lift = dao.loadLiftByID(liftIDToRemove);
		controller.deleteLift(lift);
	}

	@Test
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

	@Test
	public void listLiftAfter() {
		LocalDateTime startDateTime = LocalDateTime.parse("2020-08-10T14:00:00");
		List<Lift> results = dao.listLiftStartingAfterDateTime(startDateTime);
		for (Lift lift : results) {
			MyLogger.info("One of the result", lift);
		}
	}

	@Test
	public void listAvailableListAfter() {
		LocalDateTime startDateTime = LocalDateTime.parse("2020-08-10T15:00:00");
		List<Lift> results = dao.listAvailableLiftStartingAfterDateTime(startDateTime);
		for (Lift lift : results) {
			MyLogger.info("One of the result", lift);
		}
	}

	@Test
	public void listLiftBefore() {
		LocalDateTime stopDateTime = LocalDateTime.parse("2020-08-10T16:00:00");
		List<Lift> results = dao.listLiftStoppingBeforeDateTime(stopDateTime);
		for (Lift lift : results) {
			MyLogger.info(lift.getStopDateTime().toString());
		}
	}

	@Test
	public void listAvailableLiftBefore() {
		LocalDateTime stopDateTime = LocalDateTime.parse("2020-08-10T16:00:00");
		List<Lift> results = dao.listAvailableLiftStoppingBeforeDateTime(stopDateTime);
		for (Lift lift : results) {
			MyLogger.info("One of the result", lift);
		}
	}

	@Test
	public void addPassenger() throws DatabaseException, InvalidInputException, InvalidStateException, InterruptedException {
		Integer liftID = 2;

		Student passenger1 = dao.loadStudentByUserID("0000005");
//		Student passenger2 = dao.loadStudentByUserID(GIULIA_ID);

		Lift lift = dao.loadLiftByID(liftID);
		MyLogger.info("lift", lift);

		PassengerController pc = new PassengerController();

		try {
			pc.addPassenger(lift, passenger1);
//			pc.addPassenger(lift, passenger2);

		} catch (InvalidStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Test
	public void listPassenger() throws DatabaseException, InvalidInputException {
		Integer liftID = 5;

		List<Student> passengers = dao.listPassengersByLiftID(liftID);

		for (Student p : passengers) {
			MyLogger.info("One of the passenger", p);
		}
	}

	@Test
	public void removePassenger() throws DatabaseException, InvalidInputException {
		Integer liftID = 2;

		List<Student> passengers = new ArrayList<Student>();
		try {
			passengers = dao.listPassengersByLiftID(liftID);
			for (Student p : passengers) {
				MyLogger.info("One of the passenger", p);
			}
			Student first = passengers.get(0);

			passController.removePassenger(dao.loadLiftByID(liftID), first);
			
			MyLogger.info("Removed: " + first.getUserID());

			passengers = dao.listPassengersByLiftID(liftID);

			for (Student pNew : passengers) {
				MyLogger.info("One of the new passenger", pNew);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Test
	public void listByDriver() {

		List<Lift> driverLifts = dao.listLiftsByDriverID(MARCO_ID);

		MyLogger.info("driverLifts", driverLifts.size());
	}

	@Test
	public void listByPassenger() {

		List<Lift> passengerLifts = dao.listLiftsByPassengerID(GIUSEPPE_ID);

		MyLogger.info("passengerLifts", passengerLifts.size());
	}

}
