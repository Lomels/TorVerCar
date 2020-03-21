package logic.view.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.entity.Student;

public class MyQueries {
	
	private MyQueries() {
		throw new IllegalStateException("This class should not be instantiated.");
	}
	
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
	
	public static void addStudent(Statement stmt, Student student) throws SQLException {
		String format = "INSERT INTO Users( userID, password) VALUES ('%s', '%s');";
		String sql = String.format(format, student.getUserID(), student.getPassword());
		stmt.executeUpdate(sql);
		
		format = "INSERT INTO Students( userID, name, surname) VALUES ('%s', '%s', '%s');";
		sql = String.format(format, student.getUserID(), student.getName(), student.getSurname());
		stmt.executeUpdate(sql);		
	}
	
	public static ResultSet loadStudentByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT * FROM Users as U, Students as S WHERE U.userID = S.userID && U.userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}
	
	public static ResultSet loadPasswordByUserID(Statement stmt, String userID) throws SQLException {
		String format = "SELECT password FROM Users WHERE userID = '%s';";
		String sql = String.format(format, userID);
		return stmt.executeQuery(sql);
	}

}
