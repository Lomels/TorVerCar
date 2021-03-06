package test.utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import logic.controller.PassengerController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.exception.PassengerException;
import logic.controller.maps.CompleteMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.CarInfo;
import logic.model.Lift;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;

public class TestUtilities {

	private static final Logger LOGGER = Logger.getLogger(TestUtilities.class.getCanonicalName());

	protected static final MySqlDAO dao = new MySqlDAO();
	protected static final MapsApi maps = CompleteMapsApi.getInstance();
	private static final PassengerController PASSENGER_CONTROLLER = new PassengerController();

	public static final Integer DRIVER_NUMBER = 5;
	public static final Integer PASSENGER_NUMBER = 5;
	

	// Student Attributes
	public static final String USER_ID = "000000";
	public static final String PASSWORD = "aaaAAA123@";
	public static final String NAME = "Jova";
	public static final String SURNAME = "Notti";
	public static final String EMAIL = "dummy@torvercar.com";
	public static final String PHONE = "3334445556";

	// Car Info Attributes
	public static final String PLATE_FORMAT = "TV%sCR";
	public static final Integer SEATS = 4;
	public static final String MODEL = "Fiat Macchina";
	public static final String COLOR = "Arcobaleno";

	// Student Car Attributes
	public static final Integer RATING = 0;

	// Addresses
	public static final String ADDR_MARCO = "Via Folcarotonda 19, Palestrina";
	public static final String ADDR_GIU = "Via Prenestina Nuova 51, Palestrina";
	public static final String ADDR_ZAGA = "Zagarolo";
	public static final String ADDR_TIVOLI = "Tivoli";
	public static final String ADDR_UNI = "Via del Politecnico 1, Roma";

	// Lift JSONs
	public static final String R_MARCO_UNI = "{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";
	public static final String R_MARCO_GIU_ZAGA_UNI = "{\"distances\":[3044,9610,33507],\"durations\":[6,17,47],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":2.3917956352,\"address\":\"Zagarolo\",\"lon\":12.82922,\"lat\":41.83991},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";
	public static final String R_MARCO_GIU_TIVOLI_UNI = "{\"distances\":[3044,29155,62414],\"durations\":[6,45,82],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":2.4017963409,\"address\":\"Tivoli\",\"lon\":12.79827,\"lat\":41.96358},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";

	protected static final String[] ROUTES = { R_MARCO_UNI };

	// Lifts Attributes
	public static final String START_DATE_TIME_EARLY = "2020-08-15T19:00:00";
	public static final String START_DATE_TIME_LATE = "2020-08-15T20:00:00";
	protected static final String[] START_DATE_TIMES = { START_DATE_TIME_EARLY, START_DATE_TIME_LATE };
	protected static final Double[] MAX_DURATIONS_MULTI = { 1.5, 3.0 };
	protected static final Integer[] PASSENGERS_NUM = { 0, 4 };

	public static final String NOTE = "Ciao Mamma";

	private static int lastPassengerID = 0;

	private static boolean modified = true;

	protected TestUtilities() {
		// Do nothing
	}

	public static String generateUserID(Integer finalUserID) {
		int zeros = USER_ID.length() + 1 - finalUserID.toString().length();
		StringBuilder builder = new StringBuilder("");
		while (builder.toString().length() < zeros) {
			builder.append("0");
		}
		builder.append(finalUserID.toString());
		return builder.toString();
	}

	public static String generatePlate(Integer index) {
		String numbers = index.toString();
		StringBuilder builder = new StringBuilder(numbers);
		while (builder.toString().length() < 3) {
			builder.append("0");
		}
		return String.format(PLATE_FORMAT, builder.toString());
	}

	public static void emptyDB() throws DatabaseException {
		lastPassengerID = 0;
		dao.emptyDB();
		dbModified();
		LOGGER.log(Level.FINE, "emptyDB() completed.");
	}

	public static void populateUsers() throws InvalidInputException, DatabaseException {
		Integer indexID = 0;

		// Add Student and StudentCar
		for (; indexID < PASSENGER_NUMBER + DRIVER_NUMBER; indexID++) {
			String userID = generateUserID(indexID);
			Student student = new Student(userID, PASSWORD, EMAIL, NAME, SURNAME, PHONE);
			dao.addStudent(student);
			lastPassengerID++;
			if (indexID < DRIVER_NUMBER) {
				String plate = generatePlate(indexID);
				CarInfo carInfo = new CarInfo(plate, SEATS, MODEL, COLOR);
				StudentCar studentCar = new StudentCar(student, RATING, carInfo);
				dao.addCar(studentCar);
			}
		}
		dbModified();
		LOGGER.log(Level.FINE, "populateUser() completed.");
	}

	public static void populateLifts()
			throws DatabaseException, InvalidInputException, InvalidStateException, PassengerException {

		StudentCar driver = dao.loadStudentCarByUserID(generateUserID(0));
		// Add Lifts
		Integer liftID = 1;
		for (String routeJson : ROUTES) {
			for (String startDateTimeString : START_DATE_TIMES) {
				for (Double durationMultiplier : MAX_DURATIONS_MULTI) {
					for (Integer passengerToAdd : PASSENGERS_NUM) {
						Route route = Route.jsonDecode(new JSONObject(routeJson));
						Integer maxDuration = (int) (route.getTotalDuration() * durationMultiplier);
						LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString);
						List<Student> passengers = new ArrayList<>();
						Lift lift = new Lift(null, startDateTime, maxDuration, NOTE, driver, passengers, route);
						dao.saveLift(lift);

						addPassengerToLift(liftID++, passengerToAdd);
					}
				}
			}
		}
		dbModified();
		LOGGER.log(Level.FINE, "populateLifts() completed.");
	}

	private static void addPassengerToLift(Integer liftID, Integer passengerToAdd)
			throws InvalidInputException, DatabaseException, InvalidStateException, PassengerException {
		Lift liftFromDB = dao.loadLiftByID(liftID);
		for (int added = 0; added < passengerToAdd; added++) {

			Student passenger = addStudentToDB();

			PASSENGER_CONTROLLER.addPassenger(liftFromDB, passenger);
		}
	}

	protected static void dbModified() {
		modified = true;
	}

	public static void populateDB() {
		try {
			if (modified) {
				emptyDB();
				populateUsers();
				populateLifts();
				modified = false;
				LOGGER.log(Level.FINE, "populateDB() completed.");
			} else {
				LOGGER.log(Level.FINE, "DB was not modified from last populateDB().");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "TestUtilities not working", e);
		}
	}

	public static StudentCar getDummyDriver() throws DatabaseException, InvalidInputException {
		return dao.loadStudentCarByUserID(generateUserID(1));
	}

	public static Student getDummyStudent() throws DatabaseException, InvalidInputException {
		return dao.loadStudentByUserID(generateUserID(DRIVER_NUMBER + 1));
	}

	public static Lift getDummyLift() throws InvalidInputException, DatabaseException {
		Integer liftID = null;
		LocalDateTime startDateTime = LocalDateTime.parse(START_DATE_TIME_EARLY);
		Route route = Route.jsonDecode(new JSONObject(R_MARCO_UNI));
		int maxDuration = (int) (route.getTotalDuration() * 1.5);
		List<Student> passengers = null;
		StudentCar driver = getDummyDriver();

		return new Lift(liftID, startDateTime, maxDuration, NOTE, driver, passengers, route);
	}

	public static Student addStudentToDB() throws InvalidInputException, DatabaseException {
		Student student = new Student(generateUserID(++lastPassengerID), PASSWORD, EMAIL, NAME, SURNAME, PHONE);
		dao.addStudent(student);
		dbModified();
		return student;
	}
}