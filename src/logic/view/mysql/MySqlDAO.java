package logic.view.mysql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import logic.bean.CarInfoBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.NoResultFound;
import logic.model.CarInfo;
import logic.model.Lift;
import logic.model.Role;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.OurStudentDatabase;

public class MySqlDAO implements OurStudentDatabase {

	private static final Logger LOGGER = Logger.getLogger(MySqlDAO.class.getCanonicalName());

	private static final String USER = "torvercar";
	private static final String PASS = "ispw2020";
	private static final String URL = "jdbc:mysql://localhost:3306/TorVerCar?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";

	private static final String PASSWORD = "password";

	private static final String CANNOT_CONTACT_MESSAGE = "Could not contact the DB.";
	private static final String COLUMN_NOT_FOUND_MESSAGE = "Column not found in the DB.";
	private static final String NEXT_FAILED_MESSAGE = "Failed to fetch all entry from the DB.";

	private Statement stmt;
	private Connection conn;

	public MySqlDAO() {
		this.stmt = null;
		this.conn = null;
	}

	private void logException(Exception e) {
		LOGGER.log(Level.SEVERE, "Exception found", e);
	}

	private void connect() throws SQLException {

		this.conn = DriverManager.getConnection(URL, USER, PASS);
		this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

	}

	private void disconnect() {

		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				this.logException(e);
			}

		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				this.logException(e);
			}

	}

	private void closeAndDisconnect(ResultSet rs, boolean close) {
		if (close) {
			try {
				rs.close();
				this.disconnect();
			} catch (SQLException e) {
				// Do nothing
			}
		}
	}

	private Lift liftFromResult(ResultSet rs, boolean close) throws DatabaseException, InvalidInputException {
		try {
			// liftID
			Integer liftIDFromDB = rs.getInt("liftID");
			// startTime
			Timestamp sqlTimestamp = rs.getTimestamp("startDateTime");
			LocalDateTime startDateTime = sqlTimestamp.toLocalDateTime();
			// maxDuration
			Integer maxDuration = rs.getInt("maxDuration");
			// note
			String note = rs.getString("note");
			// driver
			String driverID = rs.getString("driverID");
			StudentCar driver = this.loadStudentCarByUserID(driverID);
			List<Student> passengers = this.listPassengersByLiftID(liftIDFromDB);
			// route
			String routeJson = rs.getString("route");
			Route route = Route.jsonDecode(new JSONObject(routeJson));
			return new Lift(liftIDFromDB, startDateTime, maxDuration, note, driver, passengers, route);
		} catch (SQLException e) {
			LOGGER.severe(e.toString());
			throw new DatabaseException(COLUMN_NOT_FOUND_MESSAGE);
		} finally {
			this.closeAndDisconnect(rs, close);
		}

	}

	private Student studentFromResult(ResultSet rs, boolean close) throws InvalidInputException, DatabaseException {
		Student student = null;
		try {
			String userID = rs.getString("userID");
			String password = rs.getString(PASSWORD);
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String email = rs.getString("email");
			String phone = rs.getString("phone");
			student = new Student(userID, password, email, name, surname, phone);
		} catch (SQLException e) {
			LOGGER.severe(e.toString());
			throw new DatabaseException(COLUMN_NOT_FOUND_MESSAGE);
		} finally {
			this.closeAndDisconnect(rs, close);
		}
		return student;
	}

	private StudentCar studentCarFromResult(ResultSet rs, boolean close)
			throws InvalidInputException, DatabaseException {
		StudentCar studentCar = null;
		try {
			String userID = rs.getString("userID");
			String password = rs.getString(PASSWORD);
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String email = rs.getString("email");
			String phone = rs.getString("phone");

			Student student = new Student(userID, password, email, name, surname, phone);

			int rating = rs.getInt("rating");

			String plate = rs.getString("plate");
			int seats = rs.getInt("seats");
			String model = rs.getString("model");
			String color = rs.getString("color");

			CarInfo carInfo = new CarInfo(plate, seats, model, color);
			studentCar = new StudentCar(student, rating, carInfo);
		} catch (SQLException e) {
			LOGGER.severe(e.toString());
			throw new DatabaseException(COLUMN_NOT_FOUND_MESSAGE);
		} finally {
			this.closeAndDisconnect(rs, close);
		}

		return studentCar;
	}

	private List<Lift> listLiftFromResult(ResultSet rs, boolean close) throws DatabaseException, InvalidInputException {
		List<Lift> result = new ArrayList<>();
		try {
			do {
				result.add(this.liftFromResult(rs, false));
			} while (rs.next());
		} catch (SQLException e) {
			throw new DatabaseException(NEXT_FAILED_MESSAGE);
		} finally {
			this.closeAndDisconnect(rs, close);
		}
		return result;
	}

	private ResultSet executeQuery(String queryMethod, Object argument) throws DatabaseException {
		Object[] arguments = { argument };
		return this.executeQuery(queryMethod, arguments);
	}

	private ResultSet executeQuery(String queryMethod, Object[] objects) throws DatabaseException {
		ResultSet resultSet = null;
		try {
			this.connect();

			Class<?>[] actualClasses = new Class<?>[objects.length + 1];
			actualClasses[0] = Statement.class;
			for (int i = 0; i < objects.length; i++)
				actualClasses[i + 1] = objects[i].getClass();

			Object[] actualObjects = new Object[objects.length + 1];
			actualObjects[0] = this.stmt;
			for (int i = 0; i < objects.length; i++)
				actualObjects[i + 1] = objects[i];

			Method method = MyQueries.class.getMethod(queryMethod, actualClasses);
			resultSet = (ResultSet) method.invoke(MyQueries.class, actualObjects);
			if (!resultSet.first()) {
				throw new NoResultFound(
						String.format("No result found for: %s with: %s.", queryMethod, Arrays.toString(objects)));
			}
			return resultSet;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			LOGGER.severe(e.toString());
			throw new DatabaseException(CANNOT_CONTACT_MESSAGE);
		} catch (SQLException e) {
			throw new DatabaseException(CANNOT_CONTACT_MESSAGE);
		}
	}

	private boolean first(String queryMethod, Object argument) throws DatabaseException {
		try {
			this.executeQuery(queryMethod, argument);
		} catch (NoResultFound e) {
			return false;
		}
		return true;
	}

	private void executeUpdate(String queryMethod, Object[] arguments) throws DatabaseException {
		try {
			this.connect();

			Class<?>[] actualClasses = new Class<?>[arguments.length + 1];
			actualClasses[0] = Statement.class;
			for (int i = 0; i < arguments.length; i++)
				actualClasses[i + 1] = arguments[i].getClass();

			Object[] actualObjects = new Object[arguments.length + 1];
			actualObjects[0] = this.stmt;
			for (int i = 0; i < arguments.length; i++)
				actualObjects[i + 1] = arguments[i];

			Method method = MyQueries.class.getMethod(queryMethod, actualClasses);
			method.invoke(MyQueries.class, actualObjects);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			LOGGER.log(Level.SEVERE, "Exception thrown", e);
		} catch (SQLException e) {
			throw new DatabaseException(CANNOT_CONTACT_MESSAGE);
		} finally {
			this.disconnect();
		}
	}

	private void executeUpdate(String queryMethod, Object argument) throws DatabaseException {
		Object[] arguments = { argument };
		this.executeUpdate(queryMethod, arguments);
	}

	@Override
	public boolean existByUserID(String userID) throws DatabaseException {
		return this.first("existByUserID", userID);
	}

	@Override
	public boolean wasBannedByUserID(String userID) throws DatabaseException {
		return this.first("wasBannedByUserID", userID);
	}

	@Override
	public void addStudent(Student student) throws DatabaseException {
		Object[] arguments = { student, Role.STUDENT.name() };
		this.executeUpdate("addStudent", arguments);
	}

	@Override
	public void addCar(StudentCar studentCar) throws DatabaseException {
		this.executeUpdate("addStudentCar", studentCar);
	}

	@Override
	public void addStudentCar(StudentCar studentCar) throws DatabaseException {
		Object[] objects = { studentCar, Role.DRIVER.name() };
		this.executeUpdate("addStudent", objects);
	}

	@Override
	public Student loadStudentByUserID(String userID) throws DatabaseException, InvalidInputException {
		ResultSet rs = this.executeQuery("loadStudentByUserID", userID);
		return this.studentFromResult(rs, true);
	}

	@Override
	public StudentCar loadStudentCarByUserID(String userID) throws DatabaseException, InvalidInputException {
		ResultSet resultSet = this.executeQuery("loadStudentCarByUserID", userID);
		return this.studentCarFromResult(resultSet, true);
	}

	@Override
	public String loadPasswordByUserID(String userID) throws DatabaseException {
		ResultSet resultSet = this.executeQuery("loadPasswordByUserID", userID);
		try {
			return resultSet.getString(PASSWORD);
		} catch (SQLException e) {
			throw new DatabaseException(COLUMN_NOT_FOUND_MESSAGE);
		}
	}

	@Override
	public void editInfoByUserID(String userID, String password, String email, String phone) throws DatabaseException {
		Object[] objects = { userID, password, email, phone };
		this.executeUpdate("updateStudent", objects);
	}

	@Override
	public void removeStudentByUserID(String userID) throws DatabaseException {
		this.executeUpdate("removeStudent", userID);
	}

	@Override
	public void removeCarByUserID(String userID) throws DatabaseException {
		this.executeUpdate("removeCarByUserID", userID);
	}

	@Override
	public void editCarInfoByUserID(String userID, CarInfoBean carInfo) throws DatabaseException {
		Object[] objects = { userID, carInfo.getPlate(), carInfo.getModel(), carInfo.getSeats(), carInfo.getColour() };
		this.executeUpdate("updateCar", objects);

	}

	@Override
	public Role loadRoleByUserID(String userID) throws DatabaseException {
		ResultSet resultSet = this.executeQuery("loadRoleByUserID", userID);
		try {
			return Role.valueOf(resultSet.getString("role"));
		} catch (SQLException e) {
			throw new DatabaseException(COLUMN_NOT_FOUND_MESSAGE);
		}
	}

	@Override
	public void saveLift(Lift lift) throws DatabaseException {
		StudentCar driver = lift.getDriver();

		if (lift.getLiftID() == null) {
			// this is the insert
			Object[] objects = { lift, driver };
			this.executeUpdate("saveLiftWithoutID", objects);
		} else {
			// first delete and then reinsert
			this.executeUpdate("deleteLiftByID", lift.getLiftID());

			Object[] objects = { lift, driver };
			this.executeUpdate("saveLiftWithID", objects);

			for (Student passenger : lift.getPassengers()) {
				Object[] objectsPassenger = { lift.getLiftID(), passenger.getUserID() };
				this.executeUpdate("addPassengerByLiftIDAndUserID", objectsPassenger);
			}
		}
	}

	@Override
	public Lift loadLiftByID(Integer liftID) throws DatabaseException, InvalidInputException {
		return this.liftFromResult(this.executeQuery("loadLiftByLiftID", liftID), true);
	}

	@Override
	public void deleteLiftByID(Integer liftID) throws DatabaseException {
		this.executeUpdate("deleteLiftByID", liftID);
	}

	@Override
	public List<Lift> listAvailableLiftStartingWithinIntervalDateTime(LocalDateTime intervalStartDateTime,
			LocalDateTime intervalStopDateTime) throws DatabaseException, InvalidInputException {
		try {
			Object[] objects = { intervalStartDateTime, intervalStopDateTime };
			ResultSet rs = this.executeQuery("listFreeLiftStartingWithinIntervalDateTime", objects);
			return this.listLiftFromResult(rs, true);
		} catch (NoResultFound e) {
			String message = String.format("No lift found starting after: %s and stopping before: %s.",
					intervalStartDateTime, intervalStopDateTime);
			LOGGER.info(message);
			return new ArrayList<>();
		}
	}

	@Override
	public void addPassengerByLiftIDAndUserID(Integer liftID, String passengerID) throws DatabaseException {
		Object[] objects = { liftID, passengerID };
		this.executeUpdate("addPassengerByLiftIDAndUserID", objects);
	}

	@Override
	public List<Student> listPassengersByLiftID(Integer liftID) throws DatabaseException, InvalidInputException {
		List<Student> result = new ArrayList<>();
		try {
			ResultSet resultSet = this.executeQuery("listPassengersByLiftID", liftID);
			do {
				result.add(this.studentFromResult(resultSet, false));
			} while (resultSet.next());
		} catch (SQLException e) {
			throw new DatabaseException(NEXT_FAILED_MESSAGE);
		} catch (NoResultFound e) {
			return result;
		}
		return result;
	}

	@Override
	public void removePassengerByLiftIDAndUserID(Integer liftID, String passengerID) throws DatabaseException {
		Object[] objects = { liftID, passengerID };
		this.executeUpdate("removePassengerByLiftIDAndUserID", objects);
	}

	@Override
	public List<Lift> listLiftsByDriverID(String driverID) throws DatabaseException, InvalidInputException {
		try {
			ResultSet resultSet = this.executeQuery("listLiftsByDriverID", driverID);
			return this.listLiftFromResult(resultSet, true);
		} catch (NoResultFound e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Lift> listLiftsByPassengerID(String passengerID) throws DatabaseException, InvalidInputException {
		try {
			ResultSet resultSet = this.executeQuery("listLiftsByPassengerID", passengerID);
			return this.listLiftFromResult(resultSet, true);
		} catch (NoResultFound e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Lift> listUnratedLiftsByPassengerID(String passengerID)
			throws DatabaseException, InvalidInputException {
		try {
			ResultSet resultSet = this.executeQuery("listUnratedLiftsByPassengerID", passengerID);
			return this.listLiftFromResult(resultSet, true);
		} catch (NoResultFound e) {
			return new ArrayList<>();
		}
	}

	@Override
	public void addNotificationByUserID(String userID, String message) throws DatabaseException {
		Object[] objects = { userID, message };
		this.executeUpdate("addNotificationByUserID", objects);
	}

	@Override
	public List<String> loadNotificationsByUserID(String userID) throws DatabaseException {
		List<String> notifications = new ArrayList<>();
		try {
			ResultSet resultSet = this.executeQuery("loadNotificationsByUserID", userID);
			do {
				notifications.add(resultSet.getString("message"));
			} while (resultSet.next());
		} catch (NoResultFound e) {
			return notifications;
		} catch (SQLException e) {
			throw new DatabaseException(COLUMN_NOT_FOUND_MESSAGE);
		}
		return notifications;
	}

	// Used only for tests
	public void emptyDB() throws DatabaseException {
		try {
			this.connect();
			String[] tables = { "Users", "Banned", "Cars", "Lifts", "Notifications", "Passengers", "Students", "Cars" };
			for (String tableName : tables) {
				MyQueries.emptyDB(stmt, tableName);
			}
			String[] tablesToReset = { "Lifts", "Notifications" };
			for (String tableName : tablesToReset) {
				MyQueries.resetTableID(stmt, tableName);
			}
		} catch (SQLException e) {
			throw new DatabaseException(CANNOT_CONTACT_MESSAGE);
		} finally {
			this.disconnect();
		}
	}

	@Override
	public void removeNotificationsByUserID(String userID) throws DatabaseException {
		this.executeUpdate("removeNotificationsByUserID", userID);
	}

	@Override
	public void upvoteRating(String userID, Integer liftID, String driverID) throws DatabaseException {
		Object[] arguments = { userID, liftID, driverID };
		this.executeUpdate("upvoteRating", arguments);
	}

	@Override
	public void downvoteRating(String userID, Integer liftID, String driverID) throws DatabaseException {
		Object[] arguments = { userID, liftID, driverID };
		this.executeUpdate("downvoteRating", arguments);
	}

	@Override
	public Lift getLastInsertedLift() throws DatabaseException, InvalidInputException {
		try {
			this.connect();
			ResultSet rs = MyQueries.getLastInsertedLift(stmt);
			if (!rs.first())
				throw new NoResultFound("No lift found.");
			return this.liftFromResult(rs, true);
		} catch (SQLException e) {
			throw new DatabaseException(CANNOT_CONTACT_MESSAGE);
		} finally {
			this.disconnect();
		}
	}

	@Override
	public Integer getLastInsertedLiftID() throws DatabaseException, InvalidInputException {
		return this.getLastInsertedLift().getLiftID();
	}

	@Override
	public boolean isRatedFromAllPassengers(Lift lift) throws DatabaseException {
		return !(this.first("listPassengerWaitingToRate", lift.getLiftID()));
	}

	@Override
	public Lift getEarliestPassengerLift(Student passenger) throws DatabaseException, InvalidInputException {
		ResultSet resultSet = this.executeQuery("getEarliestPassengerLift", passenger.getUserID());
		return this.liftFromResult(resultSet, true);
	}

	@Override
	public Lift getEarliestDriverLift(StudentCar driver) throws DatabaseException, InvalidInputException {
		ResultSet resultSet = this.executeQuery("getEarliestDriverLift", driver.getUserID());
		return this.liftFromResult(resultSet, true);
	}

}
