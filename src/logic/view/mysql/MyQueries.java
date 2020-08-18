package logic.view.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import logic.model.Student;
import logic.model.StudentCar;

public class MyQueries {

	private MyQueries() {
		throw new IllegalStateException("This class should not be instantiated.");
	}

	/// TORVERCAR DB

	public static ResultSet existByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT userID FROM Users WHERE userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}

	public static ResultSet wasBannedByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT userID FROM Banned WHERE userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}

	public static void updateStudent(Statement stmt, String userID, String password, String email, String phone)
			throws SQLException {
		String format = "UPDATE Students SET email = '%s', phone = '%s' WHERE userID = '%s';";
		String sql = String.format(format, email, phone, userID);
		stmt.executeUpdate(sql);

		format = "Update Users SET password = '%s' WHERE userID = '%s';";
		sql = String.format(format, password, userID);
		stmt.executeUpdate(sql);

	}

	public static void addStudent(Statement stmt, Student student, String role) throws SQLException {
		String format = "INSERT INTO Users( userID, password, role) VALUES ('%s', '%s', '%s');";
		String sql = String.format(format, student.getUserID(), student.getPassword(), role);
		stmt.executeUpdate(sql);

		format = "INSERT INTO Students( userID, name, surname, email, phone) VALUES ('%s', '%s', '%s', '%s', '%s');";
		sql = String.format(format, student.getUserID(), student.getName(), student.getSurname(), student.getEmail(),
				student.getPhone());
		stmt.executeUpdate(sql);
	}

	public static void addStudentCar(Statement stmt, StudentCar studentCar) throws SQLException {
		String format = "UPDATE Users SET role='DRIVER' WHERE userID='%s';";
		String sql = String.format(format, studentCar.getUserID());
		stmt.executeUpdate(sql);

		format = "INSERT INTO Cars( model, plate, seats, color, userID) VALUES ('%s', '%s', '%s', '%s', '%s');";
		sql = String.format(format, studentCar.getCarInfo().getModel(), studentCar.getCarInfo().getPlate(),
				studentCar.getCarInfo().getSeats(), studentCar.getCarInfo().getColour(), studentCar.getUserID());
		stmt.executeUpdate(sql);

	}

	public static ResultSet loadStudentByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT * FROM Users as U, Students as S WHERE U.userID = S.userID && U.userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}

	public static ResultSet loadStudentCarByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT * FROM Users as U, Students as S, Cars as C WHERE U.userID = S.userID && U.userID = C.userID && U.userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}

	public static ResultSet loadCarInfoByCarID(Statement stmt, String carID) throws SQLException {
		String format = "SELECT * FROM Cars WHERE plate = '%s';";
		String sql = String.format(format, carID);
		return stmt.executeQuery(sql);
	}

	public static ResultSet loadPasswordByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT password FROM Users WHERE userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}

	public static ResultSet loadRoleByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT role FROM Users WHERE userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}

	/// UNIVERSITY DB
	public static ResultSet getInfoByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT name, surname, email FROM students WHERE userID = '%s';";
		String sql = String.format(format, userID);

		return stmt.executeQuery(sql);
	}

	public static ResultSet existByUserId_UniDB(Statement stmt, String userID) throws SQLException {
		String format = "SELECT userID FROM students WHERE userID = '%s';";
		String sql = String.format(format, userID);

		return stmt.executeQuery(sql);
	}

	public static void updateCar(Statement stmt, String userID, String plate, String model, Integer seats, String color)
			throws SQLException {
		String format = "UPDATE Cars SET plate='%s', model='%s', seats='%s', color='%s' WHERE userID='%s';";
		String sql = String.format(format, plate, model, seats, color, userID);

		stmt.executeUpdate(sql);
	}

	public static void removeCarByUserID(Statement stmt, String userID) throws SQLException {
		String format = "DELETE FROM Cars WHERE userID = '%s';";
		String sql = String.format(format, userID);
		
		stmt.executeUpdate(sql);
		
		format = "UPDATE Students SET role='%s';";
		sql = String.format(format, "STUDENT");
		
		stmt.executeUpdate(sql);
	}
	
	public static void saveLiftWithoutID(Statement stmt, LocalDateTime startDateTime, LocalDateTime stopDateTime,
			Integer maxDuration, String note, String driverID, String route, Integer freeSeats) throws SQLException {
		String format = "INSERT INTO Lifts (startDateTime, stopDateTime, maxDuration, note, driverID, route, freeSeats) VALUES "
				+ "('%s', '%s', %d, '%s', '%s', '%s', %d);";
		String sql = String.format(format, startDateTime, stopDateTime, maxDuration, note, driverID, route, freeSeats);

		stmt.executeUpdate(sql);
	}

	public static void saveLiftWithID(Statement stmt, Integer liftID, LocalDateTime startDateTime,
			LocalDateTime stopDateTime, Integer maxDuration, String note, String driverID, String route,
			Integer freeSeats) throws SQLException {
		String format = "INSERT INTO Lifts (liftID, startDateTime, stopDateTime, maxDuration, note, driverID, route, freeSeats) VALUES "
				+ "(%d, '%s', '%s', %d, '%s', '%s', '%s', %d);";
		String sql = String.format(format, liftID, startDateTime, stopDateTime, maxDuration, note, driverID, route,
				freeSeats);

		stmt.executeUpdate(sql);
	}

	public static ResultSet loadLiftByLiftID(Statement stmt, Integer liftID) throws SQLException {
		String format = "SELECT * FROM Lifts WHERE liftID = %d;";
		String sql = String.format(format, liftID);

		return stmt.executeQuery(sql);
	}

	public static void deleteLiftByID(Statement stmt, Integer liftID) throws SQLException {
		String format = "DELETE FROM Lifts WHERE liftID = %d;";
		String sql = String.format(format, liftID);

		stmt.executeUpdate(sql);
	}

	public static ResultSet listLiftStartingAfterDateTime(Statement stmt, LocalDateTime startDateTime)
			throws SQLException {
		String format = "SELECT * FROM Lifts WHERE startDateTime > '%s';";
		String sql = String.format(format, startDateTime);

		return stmt.executeQuery(sql);
	}
	
	public static ResultSet listFreeLiftStartingAfterDateTime(Statement stmt, LocalDateTime startDateTime)
			throws SQLException {
		String format = "SELECT * FROM Lifts WHERE startDateTime > '%s' && freeSeats > 0;";
		String sql = String.format(format, startDateTime);

		return stmt.executeQuery(sql);
	}

	public static ResultSet listLiftStoppingBeforeDateTime(Statement stmt, LocalDateTime stopDateTime)
			throws SQLException {
		String format = "SELECT * FROM Lifts WHERE startDateTime < '%s';";
		String sql = String.format(format, stopDateTime);

		return stmt.executeQuery(sql);
	}

	public static ResultSet listFreeLiftStoppingBeforeDateTime(Statement stmt, LocalDateTime startDateTime)
			throws SQLException {
		String format = "SELECT * FROM Lifts WHERE stopDateTime < '%s' && freeSeats > 0;";
		String sql = String.format(format, startDateTime);

		return stmt.executeQuery(sql);
	}
	
	public static ResultSet listLiftsByDriverID(Statement stmt, String driverID) throws SQLException {
		String format = "SELECT * FROM Lifts WHERE driverID = '%s';";
		String sql = String.format(format, driverID);

		return stmt.executeQuery(sql);
	}

	public static ResultSet listLiftsByPassengerID(Statement stmt, String passengerID) throws SQLException {
		String format = "SELECT L.* FROM Passengers as P, Lifts as L WHERE P.liftID = L.liftID && P.passengerID = '%s';";
		String sql = String.format(format, passengerID);

		return stmt.executeQuery(sql);
	}


	public static ResultSet getDriverIDByLiftID(Statement stmt, Integer liftID) throws SQLException {
		String format = "SELECT driverID FROM Lifts WHERE liftID = %d;";
		String sql = String.format(format, liftID);

		return stmt.executeQuery(sql);
	}

	public static void addPassengerByLiftIDAndUserID(Statement stmt, Integer liftID, String passengerID)
			throws SQLException {
		String format = "INSERT INTO Passengers (liftID, passengerID) VALUE (%d, '%s');";
		String sql = String.format(format, liftID, passengerID);

		stmt.executeUpdate(sql);
	}

	public static ResultSet listPassengersByLiftID(Statement stmt, Integer liftID) throws SQLException {
		String format = "SELECT U.userID, password, name, surname, email, phone, rating FROM Passengers AS P, Students AS S, Users AS U WHERE P.liftID = %d && P.passengerID = S.userID && S.userID = U.userID;";
		String sql = String.format(format, liftID);

		return stmt.executeQuery(sql);
	}

	public static void removePassengerByLiftIDAndUserID(Statement stmt, Integer liftID, String passengerID)
			throws SQLException {
		String format = "DELETE FROM Passengers WHERE liftID = %d && passengerID = '%s';";
		String sql = String.format(format, liftID, passengerID);

		stmt.executeUpdate(sql);
	}

	public static void addNotificationByUserID(Statement stmt, String userID, String message) throws SQLException {
		String format = "INSERT INTO Notifications( userID, message ) VALUES ('%s', '%s');";
		String sql = String.format(format, userID, message);
		
		stmt.executeUpdate(sql);
	}

	public static ResultSet loadNotificationsByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT * FROM Notifications WHERE userID = '%s';";
		String sql = String.format(format, userID);
		
		return stmt.executeQuery(sql);
	}

}
