package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import logic.controller.PassengerController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.CarInfo;
import logic.model.Lift;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

public class TestUtilities {

	protected static final MySqlDAO dao = new MySqlDAO();
	protected static final MapsApi maps = AdapterMapsApi.getInstance();
	private static final PassengerController PASSENGER_CONTROLLER = new PassengerController();

	public static final Integer DRIVER_NUMBER = 5;
	public static final Integer PASSENGER_NUMBER = 25;

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

	// Lift JSONs
	public static final String R_MARCO_UNI = "{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";
	public static final String R_MARCO_GIU_ZAGA_UNI = "{\"distances\":[3044,9610,33507],\"durations\":[6,17,47],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":2.3917956352,\"address\":\"Zagarolo\",\"lon\":12.82922,\"lat\":41.83991},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";
	public static final String R_MARCO_GIU_TIVOLI_UNI = "{\"distances\":[3044,29155,62414],\"durations\":[6,45,82],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":2.4017963409,\"address\":\"Tivoli\",\"lon\":12.79827,\"lat\":41.96358},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";
//	protected static final String[] ROUTES = { R_MARCO_UNI, R_MARCO_GIU_ZAGA_UNI, R_MARCO_GIU_TIVOLI_UNI };
	protected static final String[] ROUTES = { R_MARCO_UNI };

	// Lifts Attributes
	public static final String START_DATE_TIME_EARLY = "2020-08-15T19:00:00";
	public static final String START_DATE_TIME_LATE = "2020-08-15T20:00:00";
	protected static final String[] START_DATE_TIMES = { START_DATE_TIME_EARLY, START_DATE_TIME_LATE };
	protected static final Double[] MAX_DURATIONS_MULTI = { 1.5, 3.0 };
	protected static final Integer[] PASSENGERS_NUM = { 0, 4 };

	public static final String NOTE = "Ciao Mamma";

	private static int passengerIdIndex = DRIVER_NUMBER;

	protected TestUtilities() {

	}

	private static String generatePlate(Integer index) {
		String numbers = index.toString();
		StringBuilder builder = new StringBuilder(numbers);
		while (builder.toString().length() < 3) {
			builder.append("0");
		}
		return String.format(PLATE_FORMAT, builder.toString());
	}

	public static void emptyDB() {
		dao.emptyDB();
		MyLogger.info("DB emptied!");
	}

	public static void populateUsers() {
		Integer indexID = 0;
		try {
			// Add Student and StudentCar
			for (; indexID < PASSENGER_NUMBER + DRIVER_NUMBER; indexID++) {
				String userID = USER_ID + indexID;
				Student student = new Student(userID, PASSWORD, EMAIL, NAME, SURNAME, PHONE);
				dao.addStudent(student);
				if (indexID < DRIVER_NUMBER) {
					String plate = generatePlate(indexID);
					CarInfo carInfo = new CarInfo(plate, SEATS, MODEL, COLOR);
					StudentCar studentCar = new StudentCar(student, RATING, carInfo);
					dao.addCar(studentCar);
				}
			}
			MyLogger.info("PopulateUsers completed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void populateLifts() {
		try {
			StudentCar driver = dao.loadStudentCarByUserID(USER_ID + "0");
			// Add Lifts
			Integer liftID = 1;
			for (String routeJson : ROUTES) {
				for (String startDateTimeString : START_DATE_TIMES) {
					for (Double durationMultiplier : MAX_DURATIONS_MULTI) {
						for (Integer passengerToAdd : PASSENGERS_NUM) {
							Route route = Route.jsonDecode(new JSONObject(routeJson));
							Integer maxDuration = (int) (route.getDuration() * durationMultiplier);
							LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString);
							List<Student> passengers = new ArrayList<>();
							Lift lift = new Lift(null, startDateTime, maxDuration, NOTE, driver, passengers, route);
							dao.saveLift(lift);

							addPassengerToLift(liftID++, passengerToAdd);
						}
					}
				}
			}
			MyLogger.info("PopulateLifts completed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void addPassengerToLift(Integer liftID, Integer passengerToAdd)
			throws DatabaseException, InvalidInputException, InvalidStateException, InterruptedException {
		Lift liftFromDB = dao.loadLiftByID(liftID);
		int finalIdIndex = passengerIdIndex + passengerToAdd;
		for (; passengerIdIndex < finalIdIndex; passengerIdIndex++) {
			Student passenger = dao.loadStudentByUserID(USER_ID + (passengerIdIndex + DRIVER_NUMBER));
			PASSENGER_CONTROLLER.addPassenger(liftFromDB, passenger);
		}
	}

	public static void populateDB() {
		emptyDB();
		populateUsers();
		populateLifts();
		MyLogger.info("PopulateDB completed.");
	}
}