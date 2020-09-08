package logic.view.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import logic.bean.CarInfoBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.CarInfo;
import logic.model.Lift;
import logic.model.Role;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.utilities.InputChecker;
import logic.utilities.MyLogger;
import logic.view.OurStudentDatabase;

public class MySqlDAO implements OurStudentDatabase {

	private static final String USER = "torvercar";
	private static final String PASS = "ispw2020";
	private static final String URL = "jdbc:mysql://localhost:3306/TorVerCar?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	private static final String PASSWORD = "password";

	private Statement stmt;
	private Connection conn;

	public MySqlDAO() {
		this.stmt = null;
		this.conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void connect() {
		try {
			this.conn = DriverManager.getConnection(URL, USER, PASS);
			this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void disconnect() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}

	}

	private Lift liftFromResult(ResultSet rs) throws SQLException, DatabaseException, InvalidInputException {
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
	}

	private Student studentFromResult(ResultSet rs) throws SQLException, InvalidInputException {
		String userID = rs.getString("userID");
		String password = rs.getString(PASSWORD);
		String name = rs.getString("name");
		String surname = rs.getString("surname");
		String email = rs.getString("email");
		String phone = rs.getString("phone");

		return new Student(userID, password, email, name, surname, phone);
	}

	private StudentCar studentCarFromResult(ResultSet rs) throws SQLException, InvalidInputException {
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

		return new StudentCar(student, rating, carInfo);
	}

	@Override
	public boolean existByUserID(String userID) throws DatabaseException, InvalidInputException {
		InputChecker.checkUserID(userID);
		boolean result = false;

		try {
			this.connect();

			ResultSet rs = null;
			rs = MyQueries.existByUserID(this.stmt, userID);
			result = rs.first(); // return true if is not empty
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public boolean wasBannedByUserID(String userID) throws InvalidInputException, DatabaseException {
		InputChecker.checkUserID(userID);
		boolean result = false;

		try {
			this.connect();
			ResultSet rs = MyQueries.wasBannedByUserID(this.stmt, userID);
			result = rs.first();
			rs.close();

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public void addStudent(Student student) throws InvalidInputException {
		try {
			this.connect();
			MyQueries.addStudent(this.stmt, student, Role.STUDENT.name());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}

	@Override
	public void addCar(StudentCar studentCar) throws DatabaseException {
		try {
			this.connect();
			MyQueries.addStudentCar(this.stmt, studentCar);

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}

	@Override
	public void addStudentCar(StudentCar studentCar) throws DatabaseException, InvalidInputException {
		try {
			this.connect();
			MyQueries.addStudent(stmt, studentCar, Role.DRIVER.name());
			MyQueries.addStudentCar(this.stmt, studentCar);

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}

	@Override
	public Student loadStudentByUserID(String userID) throws DatabaseException, InvalidInputException {
		Student s = null;
		try {
			this.connect();
			ResultSet rs = MyQueries.loadStudentByUserID(this.stmt, userID);

			if (!rs.first()) {
				String message = String.format("Student with userID: %s was not found.", userID);
				throw new DatabaseException(message);
			}

			rs.first();
			s = this.studentFromResult(rs);

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}

		return s;
	}

	@Override
	public StudentCar loadStudentCarByUserID(String userID) throws DatabaseException {
		StudentCar sCar = null;

		try {
			this.connect();
			ResultSet rs = MyQueries.loadStudentCarByUserID(this.stmt, userID);

			if (!rs.first())
				throw new DatabaseException("Student not found");

			rs.first();

			sCar = this.studentCarFromResult(rs);

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}

		return sCar;
	}

	@Override
	public String loadPasswordByUserID(String userID) throws InvalidInputException, DatabaseException {
		InputChecker.checkUserID(userID);
		String password = null;
		try {
			this.connect();

			ResultSet rs = MyQueries.loadPasswordByUserID(this.stmt, userID);

			if (!rs.first())
				throw new DatabaseException("User not found");

			rs.first();
			password = rs.getString(PASSWORD);

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
		return password;
	}

	@Override
	public void editInfoByUserID(String userID, String password, String email, String phone) throws DatabaseException {
		try {
			this.connect();
			MyQueries.updateStudent(this.stmt, userID, password, email, phone);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}

	@Override
	public void removeStudentByUserID(String userID) throws DatabaseException {
		try {
			this.connect();
			MyQueries.removeStudent(stmt, userID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}

	}

	@Override
	public void removeCarByUserID(String userID) throws DatabaseException {
		try {
			this.connect();
			MyQueries.removeCarByUserID(stmt, userID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}

	@Override
	public void editCarInfoByUserID(String userID, CarInfoBean carInfo) throws DatabaseException {
		try {
			this.connect();
			MyQueries.updateCar(this.stmt, userID, carInfo.getPlate(), carInfo.getModel(), carInfo.getSeats(),
					carInfo.getColour());

		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}

	@Override
	public Role loadRoleByUserID(String userID) throws DatabaseException {
		Role role = null;
		try {
			this.connect();
			ResultSet rs = MyQueries.loadRoleByUserID(this.stmt, userID);

			if (!rs.first())
				throw new DatabaseException("User not found");

			rs.first();
			role = Role.valueOf(rs.getString("role"));

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}

		return role;
	}

	@Override
	public void saveLift(Lift lift) throws DatabaseException {
		try {
			this.connect();
			StudentCar driver = lift.getDriver();

			if (lift.getLiftID() == null) {
				// this is the insert
				MyQueries.saveLiftWithoutID(stmt, lift.getStartDateTime(), lift.getStopDateTime(),
						lift.getMaxDuration(), lift.getNote(), driver.getUserID(),
						lift.getRoute().jsonEncode().toString(), lift.getFreeSeats());
			} else {
				// fare prima il delete e poi il reinsert
				MyQueries.deleteLiftByID(stmt, lift.getLiftID());

				MyQueries.saveLiftWithID(stmt, lift.getLiftID(), lift.getStartDateTime(), lift.getStopDateTime(),
						lift.getMaxDuration(), lift.getNote(), driver.getUserID(),
						lift.getRoute().jsonEncode().toString(), lift.getFreeSeats());

				for (Student passenger : lift.getPassengers()) {
					MyQueries.addPassengerByLiftIDAndUserID(stmt, lift.getLiftID(), passenger.getUserID());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			MyLogger.severe("catch nel saveLift del MySqlDao", e);
		} finally {
			this.disconnect();
		}

	}

	@Override
	public Lift loadLiftByID(Integer liftID) throws DatabaseException, InvalidInputException {

		Lift lift = null;
		try {
			this.connect();
			ResultSet rs = MyQueries.loadLiftByLiftID(stmt, liftID);

			if (!rs.first())
				throw new DatabaseException("Lift not found");

			rs.first();

			lift = this.liftFromResult(rs);

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
		return lift;
	}

	@Override
	public void deleteLiftByID(Integer liftID) {
		try {
			this.connect();

			MyQueries.deleteLiftByID(stmt, liftID);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	@Override
	public List<Lift> listLiftStartingAfterDateTime(LocalDateTime startDateTime) {
		List<Lift> result = new ArrayList<>();
		try {
			this.connect();

			ResultSet rs = MyQueries.listLiftStartingAfterDateTime(stmt, startDateTime);

			if (!rs.first())
				return result;

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public List<Lift> listAvailableLiftStartingAfterDateTime(LocalDateTime startDateTime) {
		List<Lift> result = new ArrayList<>();
		try {
			this.connect();

			ResultSet rs = MyQueries.listFreeLiftStartingAfterDateTime(stmt, startDateTime);

			if (!rs.first())
				return result;

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public List<Lift> listAvailableLiftStartingWithinIntervalDateTime(LocalDateTime intervalStartDateTime,
			LocalDateTime intervalStopDateTime) {
		List<Lift> result = new ArrayList<>();
		try {
			this.connect();

			ResultSet rs = MyQueries.listFreeLiftStartingWithinIntervalDateTime(stmt, intervalStartDateTime,
					intervalStopDateTime);

			if (!rs.first())
				return result;

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public List<Lift> listLiftStoppingBeforeDateTime(LocalDateTime stopDateTime) {
		List<Lift> result = new ArrayList<>();
		try {
			this.connect();

			ResultSet rs = MyQueries.listLiftStoppingBeforeDateTime(stmt, stopDateTime);

			if (!rs.first())
				return result;

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public List<Lift> listAvailableLiftStoppingBeforeDateTime(LocalDateTime stopDateTime) {
		List<Lift> result = null;
		try {
			this.connect();

			ResultSet rs = MyQueries.listFreeLiftStoppingBeforeDateTime(stmt, stopDateTime);

			if (!rs.first())
				throw new DatabaseException("No lift found stopping before" + stopDateTime.toString());

			result = new ArrayList<>();

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public List<Lift> listAvailableLiftStartingBeforeDateTime(LocalDateTime startDateTime) {
		List<Lift> result = null;
		try {
			this.connect();

			ResultSet rs = MyQueries.listFreeLiftStartingBeforeDateTime(stmt, startDateTime);

			if (!rs.first())
				throw new DatabaseException("No lift found starting before" + startDateTime.toString());

			result = new ArrayList<>();

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public String getDriverIDByLiftID(Integer liftID) {

		String result = null;
		try {
			this.connect();

			ResultSet rs = MyQueries.getDriverIDByLiftID(stmt, liftID);

			if (!rs.first())
				throw new DatabaseException("No lift with liftID: " + liftID.toString());

			rs.first();

			result = rs.getString("driverID");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public void addPassengerByLiftIDAndUserID(Integer liftID, String passengerID) {
		try {
			this.connect();

			MyQueries.addPassengerByLiftIDAndUserID(stmt, liftID, passengerID);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	@Override
	public List<Student> listPassengersByLiftID(Integer liftID) throws DatabaseException, InvalidInputException {
		List<Student> result = new ArrayList<>();
		try {
			this.connect();
			ResultSet rs = MyQueries.listPassengersByLiftID(stmt, liftID);

			if (!rs.first())
				return result;

			rs.first();
			do {
				result.add(this.studentFromResult(rs));
			} while (rs.next());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public void removePassengerByLiftIDAndUserID(Integer liftID, String passengerID) {
		try {
			this.connect();

			MyQueries.removePassengerByLiftIDAndUserID(stmt, liftID, passengerID);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}

	}

	@Override
	public List<Lift> listLiftsByDriverID(String driverID) {
		List<Lift> result = new ArrayList<>();
		try {
			this.connect();

			ResultSet rs = MyQueries.listLiftsByDriverID(stmt, driverID);

			if (!rs.first())
				return result;

			rs.first();

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public List<Lift> listLiftsByPassengerID(String passengerID) {
		List<Lift> result = new ArrayList<>();

		try {
			this.connect();

			ResultSet rs = MyQueries.listLiftsByPassengerID(stmt, passengerID);

			if (!rs.first())
				return result;

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public List<Lift> listUnratedLiftsByPassengerID(String passengerID) {
		List<Lift> result = new ArrayList<>();

		try {
			this.connect();

			ResultSet rs = MyQueries.listUnratedLiftsByPassengerID(stmt, passengerID);

			if (!rs.first())
				return result;

			do {
				result.add(this.liftFromResult(rs));
			} while (rs.next());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public void addNotificationByUserID(String userID, String message) {
		try {
			this.connect();

			MyQueries.addNotificationByUserID(stmt, userID, message);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}

	@Override
	public List<String> loadNotificationsByUserID(String userID) {
		List<String> notifications = new ArrayList<>();
		try {
			this.connect();

			ResultSet rs = MyQueries.loadNotificationsByUserID(stmt, userID);

			if (!rs.first())
				return notifications;

			rs.first();

			do {
				notifications.add(rs.getString("message"));
			} while (rs.next());

			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}

		return notifications;
	}

	// Used only in test
	public void emptyDB() {
		this.connect();
		try {
			String[] tables = { "Users", "Banned", "Cars", "Lifts", "Notifications", "Passengers", "Students", "Cars" };
			for (String tableName : tables) {
				MyQueries.emptyDB(stmt, tableName);
			}
			String[] tablesToReset = { "Lifts", "Notifications" };
			for (String tableName : tablesToReset) {
				MyQueries.resetTableID(stmt, tableName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}

	@Override
	public void removeNotificationsByUserID(String userID) {
		try {
			this.connect();
			MyQueries.removeNotificationsByUserID(stmt, userID);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}

	@Override
	public void upvoteRating(String userID, Integer liftID, String driverID) {
		try {
			this.connect();
			MyQueries.upvoteRating(stmt, userID, liftID, driverID);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}

	@Override
	public void downvoteRating(String userID, Integer liftID, String driverID) {
		try {
			this.connect();
			MyQueries.downvoteRating(stmt, userID, liftID, driverID);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
	}

	@Override
	public Lift getLastInsertedLift() throws DatabaseException, InvalidInputException {
		this.connect();
		try {
			ResultSet rs = MyQueries.getLastInsertedLift(stmt);
			if (!rs.first())
				throw new DatabaseException("No lift found.");
			rs.first();
			return this.liftFromResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return null;

	}

	@Override
	public Integer getLastInsertedLiftID() throws DatabaseException, InvalidInputException {
		this.connect();
		try {
			ResultSet rs = MyQueries.getLastInsertedLift(stmt);
			if (!rs.first())
				throw new DatabaseException("No lift found.");
			rs.first();
			return rs.getInt("liftID");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return null;
	}

	@Override
	public boolean isRatedFromAllPassengers(Lift lift) {
		this.connect();
		boolean result = false;
		try {
			ResultSet rs = MyQueries.listPassengerWaitingToRate(stmt, lift.getLiftID());
			result = !rs.first();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public Lift getEarliestPassengerLift(Student passenger) throws DatabaseException, InvalidInputException {
		this.connect();
		ResultSet rs;
		try {
			rs = MyQueries.getEarliestPassengerLift(stmt, passenger.getUserID());
			if (!rs.first()) {
				String message = String.format("Student: %s is not registered or has no booked lift.",
						passenger.getUserID());
				throw new DatabaseException(message);
			}
			rs.first();
			return this.liftFromResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return null;
	}

	@Override
	public Lift getEarliestDriverLift(StudentCar driver) throws DatabaseException, InvalidInputException {
		this.connect();
		ResultSet rs;
		try {
			rs = MyQueries.getEarliestDriverLift(stmt, driver.getUserID());
			if (!rs.first()) {
				String message = String.format("StudentCar: %s is not registered or has no offered lift.",
						driver.getUserID());
				throw new DatabaseException(message);
			}
			rs.first();
			return this.liftFromResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.disconnect();
		}
		return null;
	}

}
