package logic.view.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.model.Student;
import logic.model.StudentCar;

public class MyQueries {
	
	private MyQueries() {
		throw new IllegalStateException("This class should not be instantiated.");
	}
	
	/// TORVERCAR DB
	
	public static ResultSet existByUserID( Statement stmt, String userID) throws SQLException {
		String format = "SELECT userID FROM Users WHERE userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet wasBannedByUserID(Statement stmt, String userID) throws SQLException{
		String format = "SELECT userID FROM Banned WHERE userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}
	
	public static void updateStudent(Statement stmt, String userID, String password, String email, String phone) throws SQLException {
		String format = "UPDATE Students SET email = '%s', phone = '%s' WHERE userID = '%s';";
		String sql = String.format(format, email, phone, userID);
		stmt.executeUpdate(sql);
		
		format = "Update Users SET password = '%s' WHERE userID = '%s';";
		sql = String.format(format, password, userID);
		stmt.executeUpdate(sql);
		
	}
	
	public static void addStudent(Statement stmt, Student student, String role) throws SQLException {
		String format = "INSERT INTO Users( userID, password) VALUES ('%s', '%s');";
		String sql = String.format(format, student.getUserID(), student.getPassword());
		stmt.executeUpdate(sql);
		
		format = "INSERT INTO Students( userID, name, surname, email, phone, role) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');";
		sql = String.format(format, 
				student.getUserID(), 
				student.getName(), 
				student.getSurname(), 
				student.getEmail(), 
				student.getPhone(),
				role);
		stmt.executeUpdate(sql);		
	}
	
	
	public static void addStudentCar( Statement stmt, StudentCar studentCar) throws SQLException {
		String format = "INSERT INTO StudentsCar( userID, rating, carID) VALUES('%s', '%s', '%s');";
		String sql = String.format(format, studentCar.getUserID(), studentCar.getRating(), studentCar.getCarInfo().getPlate());
		stmt.executeUpdate(sql);
		
		format = "INSERT INTO Cars( model, plate, seats, color) VALUES ('%s', '%s', '%s', '%s');";
		sql = String.format(format, 
				studentCar.getCarInfo().getModel(),
				studentCar.getCarInfo().getPlate(),
				studentCar.getCarInfo().getSeats(),
				studentCar.getCarInfo().getColour());
		stmt.executeUpdate(sql);
	
	}	
	
	
	public static ResultSet loadStudentByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT * FROM Users as U, Students as S WHERE U.userID = S.userID && U.userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet loadStudentCarByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT * FROM StudentsCar WHERE userID = '%s';";
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
	
	
	/// UNIVERSITY DB
	public static ResultSet getInfoByUserID(Statement stmt, String userID) throws SQLException{
		String format = "SELECT name, surname, email FROM students WHERE userID = '%s';";
		String sql = String.format(format, userID);
		
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet existByUserId_UniDB(Statement stmt, String userID) throws SQLException{
		String format = "SELECT userID FROM students WHERE userID = '%s';";
		String sql = String.format(format, userID);
		
		return stmt.executeQuery(sql);
	}

	public static void updateCar(Statement stmt, String userID, String plate, String model, Integer seats, String color) throws SQLException {
		String format = "UPDATE Cars SET plate='%s', model='%s', seats='%s', color='%s' WHERE userID='%s';";
		String sql = String.format(format, plate, model, seats, color, userID);
		
		stmt.executeUpdate(sql);
		
		format = "UPDATE StudentsCar SET carID='%s' WHERE userID='%s';";
		sql = String.format(format, plate, userID);
		
		stmt.executeUpdate(sql);
	}

}
